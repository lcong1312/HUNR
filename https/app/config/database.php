<?php
declare(strict_types=1);

$defaults = [
    'host' => 'localhost',
    'port' => 3306,
    'name' => 'hunr',
    'user' => 'root',
    'password' => '',
];

$rootPath = dirname(__DIR__, 3);
$propertiesPath = $rootPath . '/src/main/resources/application.properties';

if (!is_file($propertiesPath)) {
    return $defaults;
}

$lines = file($propertiesPath, FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES);

if ($lines === false) {
    return $defaults;
}

foreach ($lines as $line) {
    $line = trim($line);

    if ($line === '' || str_starts_with($line, '#')) {
        continue;
    }

    $parts = explode('=', $line, 2);

    if (count($parts) !== 2) {
        continue;
    }

    [$key, $value] = array_map('trim', $parts);

    switch ($key) {
        case 'database.host':
            $defaults['host'] = $value;
            break;
        case 'database.port':
            $defaults['port'] = (int) $value;
            break;
        case 'database.name':
            $defaults['name'] = $value;
            break;
        case 'database.user':
        case 'spring.datasource.username':
            $defaults['user'] = $value;
            break;
        case 'spring.datasource.password':
            $defaults['password'] = $value;
            break;
        case 'spring.datasource.url':
            if (preg_match('~jdbc:mysql://([^:/?#]+)(?::(\d+))?/([^?]+)~i', $value, $matches) === 1) {
                $defaults['host'] = $matches[1];
                $defaults['port'] = isset($matches[2]) ? (int) $matches[2] : 3306;
                $defaults['name'] = $matches[3];
            }
            break;
    }
}

return $defaults;
