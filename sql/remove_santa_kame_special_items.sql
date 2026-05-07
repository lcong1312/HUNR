-- Remove items 1387, 1388, 1390, 1402, 1403, 2486 from Santa special shop
-- and from every player's bag/body/box.
--
-- Uses JSON_LENGTH/JSON_EXTRACT/JSON_REMOVE for older MariaDB versions.
-- Recommended: back up nr_player and shop tables before running.

SET @remove_item_ids = '1387,1388,1390,1402,1403,2486';

DELIMITER //

DROP FUNCTION IF EXISTS hunr_remove_santa_kame_items//

CREATE FUNCTION hunr_remove_santa_kame_items(items LONGTEXT)
RETURNS LONGTEXT
DETERMINISTIC
BEGIN
    DECLARE idx INT DEFAULT 0;
    DECLARE item_id INT DEFAULT NULL;

    IF items IS NULL OR items = '' OR JSON_VALID(items) = 0 THEN
        RETURN items;
    END IF;

    SET idx = JSON_LENGTH(items) - 1;
    WHILE idx >= 0 DO
        SET item_id = CAST(JSON_UNQUOTE(JSON_EXTRACT(items, CONCAT('$[', idx, '].id'))) AS UNSIGNED);
        IF item_id IN (1387, 1388, 1390, 1402, 1403, 2486) THEN
            SET items = JSON_REMOVE(items, CONCAT('$[', idx, ']'));
        END IF;
        SET idx = idx - 1;
    END WHILE;

    RETURN COALESCE(items, '[]');
END//

DELIMITER ;

START TRANSACTION;

-- Santa Kame special shop currently stores these rows in nr_shop_bunma_tet
-- with icon_special = 861. Keep the item_id condition so the delete is scoped.
DELETE FROM nr_shop_bunma_tet
WHERE icon_special = 861
  AND item_id IN (1387, 1388, 1390, 1402, 1403, 2486);

-- Also remove from nr_shop_santa if the same items were added there later.
DELETE FROM nr_shop_santa
WHERE item_id IN (1387, 1388, 1390, 1402, 1403, 2486);

-- Remove from player bag/body/box.
UPDATE nr_player
SET item_bag = hunr_remove_santa_kame_items(item_bag)
WHERE item_bag REGEXP '"id"[[:space:]]*:[[:space:]]*(1387|1388|1390|1402|1403|2486)';

UPDATE nr_player
SET item_body = hunr_remove_santa_kame_items(item_body)
WHERE item_body REGEXP '"id"[[:space:]]*:[[:space:]]*(1387|1388|1390|1402|1403|2486)';

UPDATE nr_player
SET item_box = hunr_remove_santa_kame_items(item_box)
WHERE item_box REGEXP '"id"[[:space:]]*:[[:space:]]*(1387|1388|1390|1402|1403|2486)';

COMMIT;

DROP FUNCTION IF EXISTS hunr_remove_santa_kame_items;
