import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Principal {

    public static void main(String[] args) throws IOException {

        //Caminhos utilizados para ler e criar arquivos, respectivamente.
        Path caminho = Paths.get("funcionarios.csv");
        Path caminhoFinal = Paths.get("func_filtrado.csv");

        //Listas utilizadas para o processamento do arquivo desejado.
        List<String> listaBaguncada = new ArrayList<>();
        List<Funcionario> listaAuxiliar = new ArrayList<>();
        List<Funcionario> listaRemover = new ArrayList<>();

        //Salva todos os elementos do arquivo .csv fornecido em um array temporario (listaTemp) e depois os repassa a uma lista (listaBaguncada).
        try {
            List<String> conteudo = Files.readAllLines(caminho);

            conteudo.forEach(linha -> {
                String listaTemp[] = linha.split(",");

                for (int i = 0; i < listaTemp.length; i++){
                    listaBaguncada.add(listaTemp[i]);
                }

            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Recolhe os elementos da listaBaguncada, os transforma em objetos (Funcionarios) e salva-os em uma nova lista (listaAuxiliar).
        for (int i = 5; i < listaBaguncada.size(); i = i + 5){

            Funcionario f1 = new Funcionario();
            f1.ID = listaBaguncada.get(i);
            f1.estadoCivil = listaBaguncada.get(i+1);
            f1.grauInstrucao = listaBaguncada.get(i+2);
            f1.numFilhos = listaBaguncada.get(i+3);
            f1.salario = listaBaguncada.get(i+4);

            listaAuxiliar.add(f1);

            }
        //Remove da lista os funcionarios que nao possuem filhos.
        listaAuxiliar.forEach(funcionario -> {

            if (funcionario.numFilhos.equalsIgnoreCase("0")){
               listaRemover.add(funcionario);
            }
        });

        listaAuxiliar.removeAll(listaRemover);

        //Cria um novo arquivo .csv somente com os funcionarios que possuem filhos.
        Files.writeString(caminhoFinal, "Identificador,Filhos,Salario\n");

        listaAuxiliar.forEach(funcionario -> {

            try {
                Files.writeString(caminhoFinal, funcionario.ID + "," + funcionario.numFilhos + "," + funcionario.salario + "\n", StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

}
