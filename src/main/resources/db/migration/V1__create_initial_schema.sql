CREATE TABLE IF NOT EXISTS wallets (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    balance DECIMAL(19,4) NOT NULL DEFAULT 0,
    total_income DECIMAL(19,4) NOT NULL DEFAULT 0,
    total_expense DECIMAL(19,4) NOT NULL DEFAULT 0,
    currency VARCHAR(10) DEFAULT 'USD',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transactions (
    id UUID PRIMARY KEY,
    wallet_id UUID NOT NULL REFERENCES wallets(id),
    amount DECIMAL(19,4) NOT NULL,
    description VARCHAR(255),
    category VARCHAR(50),
    type VARCHAR(20),
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS projections (
    id UUID PRIMARY KEY,
    wallet_id UUID NOT NULL REFERENCES wallets(id),
    remaining_fixed_expenses DECIMAL(19,4) DEFAULT 0,
    estimated_variables DECIMAL(19,4) DEFAULT 0,
    target_savings DECIMAL(19,4) DEFAULT 0,
    projection_date DATE NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
