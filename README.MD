## To solve below error

Add a spring.config.import=configserver: property to your configuration.
If configuration is not required add spring.config.import=optional:configserver: instead.
To disable this check, set spring.cloud.config.enabled=false or
spring.cloud.config.import-check.enabled=false.

## Answer

Thanks for sharing the error — this is a common issue in Spring Boot 3.4+ and Spring Cloud 2022.0+/2025.0+ due to the
new mandatory use of spring.config.import=configserver: syntax.

✅ Problem Summary
You are using Spring Cloud Config without specifying the new property:

> spring.config.import=configserver:

Spring Cloud 2022.0+ no longer reads config server automatically unless you explicitly declare this property in either:
application.yml
OR bootstrap.yml (recommended)

## in test environment

> Optional: For Local Test Failures (Unit Tests)
> If your test classes (@SpringBootTest) also crash due to config-server failure, you can:

💡 Option 1: Ignore Config Server for tests
📄 In src/test/resources/application.yml

> spring.cloud.config.enabled: false

This ensures unit tests don’t try to connect to config server.

## YAML SYNTAX ERROR FOR

> spring.config.import: configserver:

Correct & Safe Way to Write spring.config.import
YAML interprets keys like spring.config.import with colons specially. So we need to quote the value to make it valid.
> solution is
> spring.config.import: "configserver:"

##

#

##

CREATE TABLE users (
id UUID PRIMARY KEY, -- internal system-generated user ID
okta_id VARCHAR(255) UNIQUE NOT NULL, -- Okta ID of user
username VARCHAR(100) NOT NULL, -- display name or handle
email VARCHAR(150) NOT NULL UNIQUE, -- unique email for communication/login
phone_number VARCHAR(20), -- optional contact
role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'BROKER', 'TENANT')), -- access level
status VARCHAR(20) DEFAULT 'ACTIVE', -- account state: ACTIVE/SUSPENDED/DELETED
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- creation audit
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- update audit
);




CREATE TABLE properties (
id UUID PRIMARY KEY, -- property identifier
broker_id UUID NOT NULL, -- linked broker user
title VARCHAR(150) NOT NULL, -- property title (e.g., 2BHK in Pune)
description TEXT, -- full details
property_type VARCHAR(50) NOT NULL CHECK (property_type IN ('RENTAL', 'SALE')),
address_line1 VARCHAR(255),
address_line2 VARCHAR(255),
city VARCHAR(100), -- searchable field
state VARCHAR(100),
postal_code VARCHAR(20),
country VARCHAR(100),
price DECIMAL(12, 2) NOT NULL, -- listed price or rent
area_sqft DECIMAL(10,2), -- size
available BOOLEAN DEFAULT TRUE, -- availability
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_properties_broker_id ON properties(broker_id);
CREATE INDEX idx_properties_city_type ON properties(city, property_type);



CREATE TABLE contracts (
id UUID PRIMARY KEY, -- contract ID
tenant_id UUID NOT NULL, -- user ID
broker_id UUID NOT NULL, -- broker ID
property_id UUID NOT NULL, -- associated property
contract_type VARCHAR(20) CHECK (contract_type IN ('RENTAL', 'PURCHASE')),
start_date DATE NOT NULL, -- contract start
end_date DATE, -- optional for purchases
monthly_rent DECIMAL(12,2), -- applicable for RENTAL contracts
status VARCHAR(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'ACTIVE', 'TERMINATED', 'CANCELLED')),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_contracts_property ON contracts(property_id);
CREATE INDEX idx_contracts_tenant_broker ON contracts(tenant_id, broker_id);




CREATE TABLE invoices (
id UUID PRIMARY KEY, -- invoice ID
contract_id UUID NOT NULL, -- linked contract
tenant_id UUID NOT NULL, -- tenant responsible for payment
issue_date DATE NOT NULL, -- when invoice was created
due_date DATE NOT NULL, -- last day for payment
amount DECIMAL(12,2) NOT NULL,
status VARCHAR(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'PAID', 'OVERDUE')),
payment_date DATE, -- filled when paid
remarks TEXT, -- optional notes
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_invoices_contract_id ON invoices(contract_id);
CREATE INDEX idx_invoices_tenant_id ON invoices(tenant_id);



CREATE TABLE notifications (
id UUID PRIMARY KEY, -- message ID
user_id UUID NOT NULL, -- target user
type VARCHAR(50) NOT NULL CHECK (type IN ('EMAIL', 'SMS', 'INAPP')),
subject VARCHAR(255), -- notification subject
message TEXT NOT NULL, -- full message
status VARCHAR(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'SENT', 'FAILED')),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
sent_at TIMESTAMP -- actual delivery timestamp
);
CREATE INDEX idx_notifications_user_id ON notifications(user_id);



-- ---------------------------------------------
-- 📊 ENTITY RELATIONSHIP DESCRIPTION (ERD Notes)
-- ---------------------------------------------
-- 1. users.id         → properties.broker_id
-- 2. users.id         → contracts.tenant_id, contracts.broker_id
-- 3. properties.id    → contracts.property_id
-- 4. contracts.id     → invoices.contract_id
-- 5. users.id         → invoices.tenant_id, notifications.user_id



