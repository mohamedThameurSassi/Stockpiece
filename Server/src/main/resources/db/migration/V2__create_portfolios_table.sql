CREATE TABLE portfolios (
    id SERIAL PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    stock_id INT NOT NULL REFERENCES stocks(id) ON DELETE CASCADE,
    quantity INT NOT NULL DEFAULT 0,
    average_buy_price FLOAT NOT NULL DEFAULT 0,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, stock_id)
);

CREATE INDEX idx_portfolios_user_id ON portfolios(user_id);
CREATE INDEX idx_portfolios_stock_id ON portfolios(stock_id);
