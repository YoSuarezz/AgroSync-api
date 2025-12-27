CREATE SCHEMA IF NOT EXISTS agryxo;
SET search_path TO agryxo;

CREATE TABLE suscripcion (
    id UUID PRIMARY KEY,
    nombre_empresa VARCHAR(255) NOT NULL,
    direccion_empresa VARCHAR(255),
    telefono_empresa BIGINT,
    nit VARCHAR(255) NOT NULL UNIQUE,
    logo_url VARCHAR(255),
    email VARCHAR(255),
    estado_suscripcion VARCHAR(50),
    plan_suscripcion VARCHAR(50),
    fecha_inicio TIMESTAMP,
    fecha_ultimo_pago TIMESTAMP,
    fecha_proximo_cobro TIMESTAMP,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    modified_date TIMESTAMP,
    modified_by VARCHAR(255)
);

CREATE TABLE auth_user (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    id_suscripcion UUID,
    CONSTRAINT fk_auth_user_suscripcion FOREIGN KEY (id_suscripcion) REFERENCES suscripcion (id)
);

CREATE TABLE usuario (
    id UUID PRIMARY KEY,
    nombre VARCHAR(255),
    telefono VARCHAR(50),
    tipo_usuario VARCHAR(50),
    estado VARCHAR(50),
    id_suscripcion UUID,
    CONSTRAINT fk_usuario_suscripcion FOREIGN KEY (id_suscripcion) REFERENCES suscripcion (id)
);

CREATE TABLE cartera (
    id UUID PRIMARY KEY,
    id_usuario UUID UNIQUE,
    saldo_actual NUMERIC(19,2),
    total_cuentas_pagar NUMERIC(19,2),
    total_cuentas_cobrar NUMERIC(19,2),
    id_suscripcion UUID,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    modified_date TIMESTAMP,
    modified_by VARCHAR(255),
    CONSTRAINT fk_cartera_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id),
    CONSTRAINT fk_cartera_suscripcion FOREIGN KEY (id_suscripcion) REFERENCES suscripcion (id)
);

CREATE TABLE compra (
    id UUID PRIMARY KEY,
    numero_compra VARCHAR(255),
    id_proveedor UUID,
    fecha_compra DATE,
    precio_total_compra NUMERIC(19,2),
    estado VARCHAR(50),
    motivo_anulacion TEXT,
    fecha_anulacion TIMESTAMP,
    id_suscripcion UUID,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    modified_date TIMESTAMP,
    modified_by VARCHAR(255),
    CONSTRAINT fk_compra_proveedor FOREIGN KEY (id_proveedor) REFERENCES usuario (id),
    CONSTRAINT fk_compra_suscripcion FOREIGN KEY (id_suscripcion) REFERENCES suscripcion (id)
);

CREATE TABLE venta (
    id UUID PRIMARY KEY,
    numero_venta VARCHAR(255),
    id_cliente UUID,
    fecha_venta DATE,
    precio_total_venta NUMERIC(19,2),
    estado VARCHAR(50),
    motivo_anulacion TEXT,
    fecha_anulacion TIMESTAMP,
    id_suscripcion UUID,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    modified_date TIMESTAMP,
    modified_by VARCHAR(255),
    CONSTRAINT fk_venta_cliente FOREIGN KEY (id_cliente) REFERENCES usuario (id),
    CONSTRAINT fk_venta_suscripcion FOREIGN KEY (id_suscripcion) REFERENCES suscripcion (id)
);

CREATE TABLE lote (
    id UUID PRIMARY KEY,
    id_compra UUID UNIQUE,
    numero_lote VARCHAR(255),
    contramarca VARCHAR(255),
    fecha DATE,
    peso_total NUMERIC(10,2),
    id_suscripcion UUID,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    modified_date TIMESTAMP,
    modified_by VARCHAR(255),
    CONSTRAINT fk_lote_compra FOREIGN KEY (id_compra) REFERENCES compra (id),
    CONSTRAINT fk_lote_suscripcion FOREIGN KEY (id_suscripcion) REFERENCES suscripcion (id)
);

CREATE TABLE animal (
    id UUID PRIMARY KEY,
    slot VARCHAR(255),
    numero_animal VARCHAR(255),
    peso NUMERIC(19,2),
    sexo VARCHAR(50),
    id_lote UUID,
    precio_kilo_compra NUMERIC(19,2),
    precio_kilo_venta NUMERIC(19,2),
    estado VARCHAR(50),
    id_venta UUID,
    id_suscripcion UUID,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    modified_date TIMESTAMP,
    modified_by VARCHAR(255),
    CONSTRAINT fk_animal_lote FOREIGN KEY (id_lote) REFERENCES lote (id),
    CONSTRAINT fk_animal_venta FOREIGN KEY (id_venta) REFERENCES venta (id),
    CONSTRAINT fk_animal_suscripcion FOREIGN KEY (id_suscripcion) REFERENCES suscripcion (id)
);

CREATE TABLE cuenta_pagar (
    id UUID PRIMARY KEY,
    numero_cuenta VARCHAR(255),
    id_compra UUID UNIQUE,
    id_proveedor UUID,
    monto_total NUMERIC(19,2),
    saldo_pendiente NUMERIC(19,2),
    estado VARCHAR(50),
    fecha_emision DATE,
    fecha_vencimiento DATE,
    id_suscripcion UUID,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    modified_date TIMESTAMP,
    modified_by VARCHAR(255),
    CONSTRAINT fk_cuenta_pagar_compra FOREIGN KEY (id_compra) REFERENCES compra (id),
    CONSTRAINT fk_cuenta_pagar_proveedor FOREIGN KEY (id_proveedor) REFERENCES usuario (id),
    CONSTRAINT fk_cuenta_pagar_suscripcion FOREIGN KEY (id_suscripcion) REFERENCES suscripcion (id)
);

CREATE TABLE cuenta_cobrar (
    id UUID PRIMARY KEY,
    numero_cuenta VARCHAR(255),
    id_venta UUID UNIQUE,
    id_cliente UUID,
    monto_total NUMERIC(19,2),
    saldo_pendiente NUMERIC(19,2),
    estado VARCHAR(50),
    fecha_emision DATE,
    fecha_vencimiento DATE,
    id_suscripcion UUID,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    modified_date TIMESTAMP,
    modified_by VARCHAR(255),
    CONSTRAINT fk_cuenta_cobrar_venta FOREIGN KEY (id_venta) REFERENCES venta (id),
    CONSTRAINT fk_cuenta_cobrar_cliente FOREIGN KEY (id_cliente) REFERENCES usuario (id),
    CONSTRAINT fk_cuenta_cobrar_suscripcion FOREIGN KEY (id_suscripcion) REFERENCES suscripcion (id)
);

CREATE TABLE abono (
    id UUID PRIMARY KEY,
    id_cuenta_pagar UUID,
    monto NUMERIC(19,2),
    fecha_pago TIMESTAMP,
    metodo_pago VARCHAR(50),
    concepto TEXT,
    estado VARCHAR(50),
    motivo_anulacion TEXT,
    fecha_anulacion TIMESTAMP,
    id_suscripcion UUID,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    modified_date TIMESTAMP,
    modified_by VARCHAR(255),
    CONSTRAINT fk_abono_cuenta_pagar FOREIGN KEY (id_cuenta_pagar) REFERENCES cuenta_pagar (id),
    CONSTRAINT fk_abono_suscripcion FOREIGN KEY (id_suscripcion) REFERENCES suscripcion (id)
);

CREATE TABLE cobro (
    id UUID PRIMARY KEY,
    id_cuenta_cobrar UUID,
    monto NUMERIC(19,2),
    fecha_cobro TIMESTAMP,
    metodo_pago VARCHAR(50),
    concepto TEXT,
    estado VARCHAR(50),
    motivo_anulacion TEXT,
    fecha_anulacion TIMESTAMP,
    id_suscripcion UUID,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    modified_date TIMESTAMP,
    modified_by VARCHAR(255),
    CONSTRAINT fk_cobro_cuenta_cobrar FOREIGN KEY (id_cuenta_cobrar) REFERENCES cuenta_cobrar (id),
    CONSTRAINT fk_cobro_suscripcion FOREIGN KEY (id_suscripcion) REFERENCES suscripcion (id)
);

-- Datos base
INSERT INTO suscripcion (
    id,
    nombre_empresa,
    direccion_empresa,
    telefono_empresa,
    nit,
    logo_url,
    email,
    estado_suscripcion,
    plan_suscripcion,
    fecha_inicio,
    fecha_ultimo_pago,
    fecha_proximo_cobro,
    created_date,
    modified_date
) VALUES (
    '00000000-0000-0000-0000-000000000001',
    'Agryxo',
    'Direccion Agryxo',
    3127054715,
    '111111111-1',
    'https://example.com/logo.png',
    'agryxo@gmail.com',
    'ACTIVA',
    'ANUAL',
    '2025-01-01 00:00:00',
    '2025-12-01 00:00:00',
    '2026-12-01 00:00:00',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
) ON CONFLICT (id) DO NOTHING;

INSERT INTO auth_user (
    id,
    email,
    password,
    rol,
    id_suscripcion,
    activo
) VALUES (
    '00000000-0000-0000-0000-000000000002',
    'admin@agryxo.com',
    '$2a$10$dnHcCv9OXKFAGUp1UyvCAOAD9wPFl9ONrDlcthUzbJv2jNLg14Ez2',
    'SUPER_ADMINISTRADOR',
    '00000000-0000-0000-0000-000000000001',
    TRUE
) ON CONFLICT (id) DO NOTHING;
