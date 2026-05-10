# =====================================================
# Script backup nr_player & nr_user từ bảng staging
# Chạy mỗi 5 phút bằng Windows Task Scheduler
# Giữ lại 288 bản backup (24 giờ)
# =====================================================

# --- Cấu hình ---
$MYSQL = "C:\xampp\mysql\bin\mysql.exe"
$DB_HOST = "127.0.0.1"
$DB_PORT = "3306"
$DB_NAME = "hunr"
$DB_USER = "root"
$DB_PASS = ""

$BACKUP_DIR = "C:\xampp\mysql\data\nr_backup"
$MAX_BACKUPS = 288

# --- Tạo thư mục ---
if (-not (Test-Path $BACKUP_DIR)) {
    New-Item -ItemType Directory -Path $BACKUP_DIR -Force | Out-Null
}

# --- Timestamp ---
$ts = Get-Date -Format "yyyy-MM-dd_HH-mm-ss"

# --- Hàm xuất 1 bảng ---
function Export-Table {
    param(
        [string]$TableName,
        [string]$FileName
    )

    $filePath = Join-Path $BACKUP_DIR $FileName
    $header = @"
-- Backup $TableName - $ts
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE ``$TableName``;

"@
    $footer = @"

SET FOREIGN_KEY_CHECKS=1;
"@

    # Ghi header
    Set-Content -Path $filePath -Value $header -Encoding UTF8

    # Query data từ staging table
    $sql = "SELECT sql_line FROM _export_stage WHERE table_name='$TableName' ORDER BY line_order"
    $result = & $MYSQL -h $DB_HOST -P $DB_PORT -u $DB_USER --password=$DB_PASS -D $DB_NAME -N -e $sql 2>$null

    if ($result) {
        Add-Content -Path $filePath -Value $result -Encoding UTF8
    }

    # Ghi footer
    Add-Content -Path $filePath -Value $footer -Encoding UTF8
}

# --- Xuất cả 3 bảng ---
Export-Table -TableName "nr_player" -FileName "nr_player_$ts.sql"
Export-Table -TableName "nr_user" -FileName "nr_user_$ts.sql"
Export-Table -TableName "nr_disciple" -FileName "nr_disciple_$ts.sql"

# --- Xóa bản backup cũ nếu vượt quá MAX_BACKUPS ---
$playerFiles = Get-ChildItem -Path $BACKUP_DIR -Filter "nr_player_*.sql" | Sort-Object CreationTime

if ($playerFiles.Count -gt $MAX_BACKUPS) {
    $toDelete = $playerFiles | Select-Object -First ($playerFiles.Count - $MAX_BACKUPS)
    foreach ($f in $toDelete) {
        Remove-Item $f.FullName -Force
        # Xóa file nr_user cùng timestamp
        $userFile = $f.FullName -replace "nr_player_", "nr_user_"
        if (Test-Path $userFile) {
            Remove-Item $userFile -Force
        }
    }
}

Write-Host "[$(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')] Backup done: nr_player_$ts.sql, nr_user_$ts.sql"
