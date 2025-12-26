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
    plan_suscripcion,
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
    'MENSUAL',
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
    id_suscripcion,
    activo
) VALUES (
    '00000000-0000-0000-0000-000000000002',
    'admin@agrosync.com',
    '$2a$10$dnHcCv9OXKFAGUp1UyvCAOAD9wPFl9ONrDlcthUzbJv2jNLg14Ez2',
    'SUPER_ADMINISTRADOR',
    '00000000-0000-0000-0000-000000000001',
    true
)
ON CONFLICT (id) DO NOTHING;
-- =====================================================
-- CREDENCIALES DE PRUEBA:
    -- Email: admin@agrosync.com
-- Password: 123456789
-- =====================================================
