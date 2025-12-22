-- =====================================================
-- DATOS DE PRUEBA PARA SUSCRIPCIÓN
-- =====================================================
-- UUID de prueba para usar en relaciones: 00000000-0000-0000-0000-000000000001
-- =====================================================

INSERT INTO suscripcion (
    id,
    nombre_empresa,
    direccion_empresa,
    telefono_empresa,
    nit,
    logo_url,
    email,
    estado_suscripcion,
    fecha_inicio,
    fecha_ultimo_pago,
    fecha_proximo_cobro,
    created_date,
    modified_date
) VALUES (
    '00000000-0000-0000-0000-000000000001',
    'AgroSync Demo',
    'Calle 123 #45-67',
    3001234567,
    '900123456-7',
    'https://example.com/logo.png',
    'contacto@agrosync.com',
    'ACTIVA',
    '2024-01-01 00:00:00',
    '2024-12-01 00:00:00',
    '2025-01-01 00:00:00',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
)
ON CONFLICT (id) DO NOTHING;

-- =====================================================
-- DATOS DE PRUEBA PARA AUTH USER (ADMIN)
-- =====================================================
INSERT INTO auth_user (
    id,
    email,
    password,
    rol,
    id_suscripcion
) VALUES (
    '00000000-0000-0000-0000-000000000002',
    'admin@agrosync.com',
    '$2a$10$dnHcCv9OXKFAGUp1UyvCAOAD9wPFl9ONrDlcthUzbJv2jNLg14Ez2',
    'ADMINISTRADOR',
    '00000000-0000-0000-0000-000000000001'
)
ON CONFLICT (id) DO NOTHING;
-- =====================================================
-- CREDENCIALES DE PRUEBA:
    -- Email: admin@agrosync.com
-- Password: 123456789
-- =====================================================

-- =====================================================
-- ACTUALIZACIÓN DE CONSTRAINS PARA MÉTODO DE PAGO
-- =====================================================
-- Actualizar constraint de abono_metodo_pago_check para incluir CRUCE_DE_CUENTAS
-- =====================================================

-- Eliminar la constraint existente si existe
ALTER TABLE abono DROP CONSTRAINT IF EXISTS abono_metodo_pago_check;

-- Recrear la constraint con todos los valores del enum
ALTER TABLE abono ADD CONSTRAINT abono_metodo_pago_check
    CHECK (metodo_pago IN ('EFECTIVO', 'TRANSFERENCIA', 'TARJETA_CREDITO', 'TARJETA_DEBITO', 'OTRO', 'CRUCE_DE_CUENTAS'));

-- =====================================================
-- Actualizar constraint de cobro_metodo_pago_check para incluir CRUCE_DE_CUENTAS
-- =====================================================

-- Eliminar la constraint existente si existe
ALTER TABLE cobro DROP CONSTRAINT IF EXISTS cobro_metodo_pago_check;

-- Recrear la constraint con todos los valores del enum
ALTER TABLE cobro ADD CONSTRAINT cobro_metodo_pago_check
    CHECK (metodo_pago IN ('EFECTIVO', 'TRANSFERENCIA', 'TARJETA_CREDITO', 'TARJETA_DEBITO', 'OTRO', 'CRUCE_DE_CUENTAS'));
