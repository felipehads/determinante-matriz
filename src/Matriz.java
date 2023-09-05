import java.util.Arrays;
import java.util.Scanner;


class Matriz {
    private final int [][] matriz;
    private final int ordemMatriz;
    private final int ultimoIndiceMatriz;

    public Matriz(int[][] matriz) {
        this.matriz = matriz;
        this.ordemMatriz = matriz.length;
        this.ultimoIndiceMatriz = this.ordemMatriz - 1;
    }

    public int calcularDeterminante() {
        if (this.ordemMatriz <= 2) return this.obtemDeterminanteParaMatrizesPequenas();
        return this.calcularDiagonaisPrimarias() -  this.calcularDiagonaisSecundarias();
    }

    private int obtemDeterminanteParaMatrizesPequenas() {
        return this.ordemMatriz == 1 ? this.matriz[0][0] : this.matriz[0][0] * this.matriz[1][1] - this.matriz[0][1] * this.matriz[1][0];
    }

    private int calcularDiagonaisPrimarias() {

        int [] produtoDeDiagonalPorIndice = this.obterArrayInicialDeProdutoDeDiagonais();
        //        [0] -> [0][0] * [1][1] * [2][2]
        //        [1] -> [0][1] * [1][2] * [2][0]
        //        [2] -> [0][2] * [1][0] * [2][1]

        for (int linha = 0; linha <= this.ultimoIndiceMatriz; linha++) {
            for (int coluna = linha; coluna < linha + this.ordemMatriz; coluna++) {
                produtoDeDiagonalPorIndice[coluna - linha] *= this.matriz[linha][this.obterIndiceTratadoDaMatriz(coluna)];
            }
        }

        return this.obterSomaDeProdutoDasDiagonais(produtoDeDiagonalPorIndice);
    }

    private int calcularDiagonaisSecundarias() {

        int [] produtoDeDiagonalPorIndice = this.obterArrayInicialDeProdutoDeDiagonais();
        //        [0] -> [2][0] * [1][1] * [0][2]
        //        [1] -> [2][1] * [1][2] * [0][0]
        //        [2] -> [2][2] * [1][0] * [0][1]

        for (int linha = this.ultimoIndiceMatriz; linha >= 0; linha--) {

            int primeiroIndiceJ = this.ultimoIndiceMatriz - linha;
            int indiceArrayDiagonais = 0;

            for (int coluna = primeiroIndiceJ; coluna < primeiroIndiceJ + this.ordemMatriz; coluna++) {
                produtoDeDiagonalPorIndice[indiceArrayDiagonais++] *= this.matriz[linha][this.obterIndiceTratadoDaMatriz(coluna)];
            }
        }

        return this.obterSomaDeProdutoDasDiagonais(produtoDeDiagonalPorIndice);
    }

    private int obterSomaDeProdutoDasDiagonais(int [] diagonais) {
        return Arrays.stream(diagonais).reduce(0, Integer::sum);
    }

    private int[] obterArrayInicialDeProdutoDeDiagonais() {
        int [] diagonais = new int[this.ordemMatriz];
        Arrays.fill(diagonais, 1);
        return diagonais;
    }

    private int obterIndiceTratadoDaMatriz(int indice) {
        return indice > this.ultimoIndiceMatriz ? indice - this.ordemMatriz : indice;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Informe a ordem da matriz: ");

        int ordemMatriz = scanner.nextInt();

        if (ordemMatriz <= 0) {
            System.out.println("Quantidade inválida");
            return;
        }

        int [][] matriz = new int[ordemMatriz][ordemMatriz];

        for (int linha = 0; linha < ordemMatriz; linha++) {
            for (int coluna = 0; coluna < ordemMatriz; coluna++) {
                System.out.println("Digite o valor da posição [" + linha + "," + coluna + "]: ");
                matriz[linha][coluna] = scanner.nextInt();
            }
        }

        scanner.close();

        Matriz m = new Matriz(matriz);

        System.out.println("O determinante da matriz é: " + m.calcularDeterminante());
    }
}