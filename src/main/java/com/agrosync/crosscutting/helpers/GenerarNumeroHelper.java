package com.agrosync.crosscutting.helpers;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Helper para la generación de números de documentos del sistema.
 * Centraliza la lógica de generación de identificadores únicos para
 * compras, ventas, lotes, animales y cuentas.
 */
public final class GenerarNumeroHelper {

    /**
     * Constructor privado para evitar instanciación
     */
    private GenerarNumeroHelper() {
    }

    /**
     * Genera un número de compra con formato: CO-{contramarca}-{4 dígitos aleatorios}
     *
     * @param contramarca contramarca asociada a la compra
     * @return número de compra generado
     */
    public static String generarNumeroCompra(String contramarca) {
        return String.format(
                "CO-%s-%s",
                TextHelper.getDefault(contramarca, TextHelper.EMPTY),
                generarDigitosAleatorios(4)
        );
    }

    /**
     * Genera un número de venta con formato: VE-{4 dígitos aleatorios}
     *
     * @return número de venta generado
     */
    public static String generarNumeroVenta() {
        return String.format("VE-%s", generarDigitosAleatorios(4));
    }

    /**
     * Genera un número de lote con formato: {contramarca}-{semana}-{año}
     *
     * @param contramarca contramarca del lote
     * @param fecha fecha asociada al lote
     * @return número de lote generado
     */
    public static String generarNumeroLote(String contramarca, LocalDate fecha) {
        LocalDate fechaLote = ObjectHelper.getDefault(fecha, LocalDate.now());
        int semana = fechaLote.get(WeekFields.ISO.weekOfWeekBasedYear());

        return String.format(
                "%s-%d-%d",
                TextHelper.getDefault(contramarca, " "),
                semana,
                fechaLote.getYear()
        );
    }

    /**
     * Genera un número de animal con formato: {contramarca}-{slot}-{semana}-{año}
     *
     * @param contramarca contramarca del animal
     * @param slot posición o slot del animal
     * @param fecha fecha asociada al animal
     * @return número de animal generado
     */
    public static String generarNumeroAnimal(String contramarca, String slot, LocalDate fecha) {
        LocalDate fechaAnimal = ObjectHelper.getDefault(fecha, LocalDate.now());
        int semana = fechaAnimal.get(WeekFields.ISO.weekOfWeekBasedYear());

        return String.format(
                "%s-%s-%02d-%d",
                TextHelper.getDefault(contramarca, TextHelper.EMPTY),
                TextHelper.getDefault(slot, TextHelper.EMPTY),
                semana,
                fechaAnimal.getYear()
        );
    }

    /**
     * Genera un número de cuenta por pagar con formato: CXP-{numeroCompra}
     *
     * @param numeroCompra número de compra asociado
     * @return número de cuenta por pagar generado
     */
    public static String generarNumeroCuentaPagar(String numeroCompra) {
        return String.format(
                "CXP-%s",
                TextHelper.getDefault(numeroCompra, TextHelper.EMPTY)
        );
    }

    /**
     * Genera un número de cuenta por cobrar con formato: CXC-{numeroVenta}
     *
     * @param numeroVenta número de venta asociado
     * @return número de cuenta por cobrar generado
     */
    public static String generarNumeroCuentaCobrar(String numeroVenta) {
        return String.format(
                "CXC-%s",
                TextHelper.getDefault(numeroVenta, TextHelper.EMPTY)
        );
    }

    /**
     * Genera una cadena de dígitos aleatorios de la longitud especificada
     *
     * @param cantidad cantidad de dígitos
     * @return cadena de dígitos aleatorios
     */
    private static String generarDigitosAleatorios(int cantidad) {
        int limite = (int) Math.pow(10, cantidad);
        int numero = ThreadLocalRandom.current().nextInt(limite);
        return String.format("%0" + cantidad + "d", numero);
    }
}