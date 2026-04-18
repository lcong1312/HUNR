<?php
declare(strict_types=1);

return [
    'payos' => [
        'base_url' => 'https://api-merchant.payos.vn',
        'client_id' => getenv('PAYOS_CLIENT_ID') ?: '38214568-ed8c-404f-b26b-ff6370c05841',
        'api_key' => getenv('PAYOS_API_KEY') ?: '95a9b932-475e-4efb-987b-ffe298c760f1',
        'checksum_key' => getenv('PAYOS_CHECKSUM_KEY') ?: '54275b29e5f4c2b5af08d127dd3f5b77b59015c047abf1fe39474f063e2cac67',
    ],
    'bank' => [
        'name' => 'MBBank',
        'account_name' => 'LE VIET CONG',
        'account_number' => '0334333196',
    ],
    'limits' => [
        'min_amount' => 1000,
        'max_amount' => 50000000,
    ],
    'storage' => [
        'table' => 'bank_topup_orders',
    ],
];
