package application;

public class UI {
	public static void mainMenu() {
		System.out.println("--- Clínica de Consultas Ágil ---");
		System.out.println("---       Menu principal      ---");
		System.out.println("1. Cadastrar um paciente");
		System.out.println("2. Marcações de consultas");
		System.out.println("3. Cancelamento de consultas");
		System.out.println("4. Sair");
		System.out.print("Selecione uma opção: ");
	}

	public static void consultationManager() {
		System.out.println("--- Opções ---");
		System.out.println("1. Remarcar");
		System.out.println("2. Cancelar");
		System.out.println("3. Voltar");
		System.out.print("Selecione uma opção: ");
	}
}
