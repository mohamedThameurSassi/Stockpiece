CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    berry_balance FLOAT DEFAULT 10000,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE stocks (
    id SERIAL PRIMARY KEY,
    character_name VARCHAR(255) NOT NULL,
    ticker VARCHAR(10) UNIQUE NOT NULL,
    bounty_percentage FLOAT NOT NULL,
    current_price FLOAT NOT NULL,
    previous_price FLOAT,
    circulating_supply BIGINT,
    ma50 FLOAT,
    ma200 FLOAT,
    rsi FLOAT,
    recent_net_volume INT DEFAULT 0,
    volume_24h INT DEFAULT 0,
    image_url VARCHAR(500),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE stock_orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    stock_id INT NOT NULL REFERENCES stocks(id) ON DELETE CASCADE,
    order_type VARCHAR(20) NOT NULL,
    quantity INT NOT NULL,
    price_per_share FLOAT NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    filled_at TIMESTAMP
);

CREATE INDEX idx_stock_orders_user_id ON stock_orders(user_id);
CREATE INDEX idx_stock_orders_stock_id ON stock_orders(stock_id);
CREATE INDEX idx_stock_orders_status ON stock_orders(status);
