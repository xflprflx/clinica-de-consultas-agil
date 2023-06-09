package model.util;

import java.util.regex.Pattern;

public class Formatting {
	
	
    public static String removeSpaces(String text) {
        // Substitui múltiplos espaços seguidos por apenas um espaço
        text = text.replaceAll("\\s+", " ");
        
        // Remove espaços no início e no final do texto
        text = text.trim();
        
        return text;
    }
    
    public static boolean validTelephone(String number) {
        // Define o padrão regex para um número de telefone válido sem o código do país
        String regexPattern = "^\\(?\\d{2,3}\\)?\\s*\\d{3,4}\\s*-?\\s*\\d{3,4}$";
        
        // Compila o padrão regex em uma expressão regular
        Pattern pattern = Pattern.compile(regexPattern);
        
        // Verifica se o número de telefone corresponde ao padrão regex
        return pattern.matcher(number).matches();
    }


    public static boolean validateFormatDate(String day) {
        // Define o padrão regex para o formato da data (dd/MM/yyyy)
        String regexPattern = "^\\d{2}/\\d{2}/\\d{4}$";
        
        // Compila o padrão regex em uma expressão regular
        Pattern pattern = Pattern.compile(regexPattern);
        
        // Verifica se a data corresponde ao padrão regex
        return pattern.matcher(day).matches();
    }
    
    public static boolean validateFormatHour(String hour) {
        // Define o padrão regex para o formato da hour (HH:mm)
        String regexPattern = "^\\d{2}:\\d{2}$";
        
        // Compila o padrão regex em uma expressão regular
        Pattern pattern = Pattern.compile(regexPattern);
        
        // Verifica se a hour corresponde ao padrão regex
        return pattern.matcher(hour).matches();
    }
}
