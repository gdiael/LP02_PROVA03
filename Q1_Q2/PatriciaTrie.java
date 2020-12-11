// Q1
// ENUNCIADO:
// Implemente uma árvore Patricia em que as chaves são cadeias de caracteres que são convertidas pelas
// operações em cadeias da valores booleanos.
//
// ESTRATÉGIA PARA RESOLUÇÃO
// Foram criadas duas classes, uma chamada Node, para representar os nós da árvore Patricia, ela possue 4
// propriedades, uma para a chave, uma para o indice do caractere de decisão, e uma pra cada filho.
// também possue métodos para: verificar se é um nó folha, criar um clone (novo objeto com as mesmas propriedades).
// A classe que representa a arvore patrícia, foi chamada de PatriciaTrie e possue apenas uma propriedade, para o nó
// raiz. Para ela foram criados os seguintes métodos:
//   - Para converter uma String de letras em uma String de zeros e uns.
//   - Para converter uma String de zeros e uns em uma de letras.
//   - Para comparar duas String e retornar o indice do primeiro caractere que difere entre elas.
//   - Para percorrer a arvore usando os caracteres de decisão com base até encontrar uma nó folha, que será o nó com
//     com a chave igual ou mais próxima, caso não tenham caracteres de decisão compatíveis o caminho padrão é pela
//     esquerda.
//   - Para percorrer a arvore, como o método anterior, porém ao encontrar o nó folha, será calculado e armazenado
//     um novo indice de decisão, que será usado na volta das chamadas recursivas para decider o nó de inserção
//   - Para adicionar novas chamdas, fazendo uso do método anterior.
//
// Q2
// ENUNCIADO:
// Utilize a árvore Patricia para armazenar um conjunto de 30 palavras diferentes. A primeira letra X de cada
// palavra deve ser a mesma que a primeira letra do seu nome Y , isto é, X = Y . Utilizando essa árvore, crie
// um método que recebe uma cadeia de caracteres em que a primeira letra também é X e retorna todas as
// palavras presentes na árvore que possuem o mesmo prefixo.
// 
// ESTRATÉGIA PARA RESOLUÇÃO:
// No método mais da PatriciaTrie foi declarado um array de Strings com uma lista de 30 palavras, todas iniciando
// pela letra G, foram usadas pelavras curtas, apenas em letras maiusculas. Em seguida se instaciou uma PatriciaTrie
// e foram inseridas as palavras presentes na array como chaves na arvore Patricia.
// Após isso, foi impressa a arvore para verificá-la.
// Em seguida foi chamado o método getSamePref:
// - Nesse método é passado como parâmetro uma String representando um prefixo que será usada para a busca de
//   com esse mesmo prefixo. Para isso, inicialmente o prefixo é convertido para a string binária, em seguida
//   o método getDifNode é usado para percorrer a arvore em busca do nó no qual o prefixo poderia ser inserido,
//   todos os nós da subarvore desse nó dever ter o mesmo prefixo que o fornecido, caso o nó retornado por getDifNode
//   não seja um nó folha, percorrermos os nós a esquerda até encontrar um nó folha e verificamos se o prefixo bate
//   caso o prefiso bate, os nós da subarvore falada será percorridos em preordem e sempre que seja encontrado um
//   nó folha, sua chave será convertida de volta e adicionada numa lista.
// 
// a impressão da arvore segue o esquema
//  [decisão] ┬─> |direita|chave
//            └─> |esquerda|chave

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PatriciaTrie {
    private Node root;

    public class Node{

        // valor contido no nó (atualmente inteiro)
        public String value;
        // posição da cadeia de caracteres em relação as chaves
        public Integer pos;
        // referência ao nó filho, à diretia
        public Node right;
        // referência ao nó filho, à esquerda
        public Node left;
    
        // construtor com a posição
        public Node(String value, Integer pos){
            this.value = value;
            this.pos = pos;
        }

        // construtor onde a posição inicial será o comprimento do string
        public Node(String value){
            this.value = value;
            this.pos = value.length();
        }
    
        // verifica se tem nó filho a direita
        public Boolean hasRight(){
            return this.right != null;
        }
    
        // verifica se tem nó filho a esquerda
        public Boolean hasLeft(){
            return this.left != null;
        }
    
        // verifica se o tem está cheio (tem filho a direita e a esquerda)
        public Boolean isFull(){
            return this.hasRight() && this.hasLeft();
        }
       
        // verifica se o nó está vazio (não tem filhos)
        public Boolean isLeaf(){
            return !this.hasRight() && !this.hasLeft();
        }
        
        public Node clone(){
            Node newNo = new Node(this.value, this.pos);
            newNo.left = this.left;
            newNo.right = this.right;
            return newNo;
        }

        // imprime este nó e seus filhos
        public String toString(){
            String strBase = this.pos.toString();
            if(this.isLeaf()){
                strBase = "|" + strBase + "|" + PatriciaTrie.binToChar(this.value);// + " " + this.value;
            } else {
                strBase = "[" + strBase + "]";
            }
            return strBase;
        }

        public String subTreeString(String prefix){
            String val = this.toString();
            if(this.isLeaf()) return val + "\n";
            String valSpace = "";
            for(int i = 0; i < val.length(); i++){
                valSpace += " ";
            }
            String dStr = " \u252C\u2500> " + (this.hasRight() ? this.right.subTreeString(prefix + valSpace + " \u2502   "): "*\n");
            String eStr = prefix + valSpace + " \u2514\u2500> " + (this.hasLeft() ? this.left.subTreeString(prefix + valSpace + "     ") : "*\n");
            return val + dStr + eStr;
        }
    }

    public PatriciaTrie(){
    }

    public static void main(String[] args) {
        System.out.println("");
        System.out.println("Iniciando instancias da classe Tree");
        System.out.println("");

        PatriciaTrie arv = new PatriciaTrie();
        String[] gWords = {"GATO", "GALO", "GAZELA", "GRAMA", "GREMIO", "GANSO",
                           "GOSTO", "GASTO", "GESTO", "GESSO", "GALINHA", "GALHO",
                           "GORDO", "GEMA", "GROSSO", "GRILO", "GRÃO", "GRALHA",
                           "GERME", "GRELHA", "GRANDE", "GÍRIA", "GRATO", "GREGO", 
                           "GRATUITO", "GENTE", "GRAU", "GRAFO", "GRIFO", "GULA"};
        for (String str : gWords) {
            arv.add(str);
        }
        System.out.println("Imprimindo a arvore PATRICIA");
        System.out.println(arv);
        System.out.println("Imprimindo chaves da arvore PATRICIA com prefixo GE:");
        List<String> wordList = arv.getSamePref("GE");
        for (String str : wordList) {
            System.out.print(str + " | ");
        }

    }

    public static String charToBin(String value){
        byte[] byteArray = value.getBytes();
        BigInteger intBase = new BigInteger(byteArray);
        return intBase.toString(2);
    }

    public static String binToChar(String value){
        BigInteger intBase = new BigInteger(value, 2);
        byte[] byteArray = intBase.toByteArray();
        return new String(byteArray);
    }

    private int getDecPos(String str1, String str2){
        if(str1 == "" || str2 == "") return -1;
        int len = Math.min(str1.length(), str2.length());
        int i = 0;
        while(i < len && str1.charAt(i) == str2.charAt(i)){
            i+=1;
        }
        return i;
    }

    private Node getNode(Node no, String value){
        if(no == null) return no;
        if(no.isLeaf()) return no;
        // a partir daqui temos um nó de decisão
        if(no.pos >= value.length() || value.charAt(no.pos) == '0'){
            return this.getNode(no.left, value);
        } else {
            return this.getNode(no.right, value);
        }
    }

    // percorre toda a arvore até encontrar um nó folha, ao encontrar, reserva em pos[0] o indice do caractere de decisão,
    // no retorno das chamadas recursivas usa o valor em pos[0] para comparar esse valor nos nós anteriores, até encontrar
    // a posição onde o nó deve ser inserido;
    private Node getDifNode(Node no, String value, int[] pos){
        if(no == null) return no;
        if(no.isLeaf()){
            pos[0] = this.getDecPos(no.value, value);
            return no;
        } 
        // a partir daqui temos um nó de decisão
        if(no.pos >= value.length() || value.charAt(no.pos) == '0'){
            Node lNo = this.getDifNode(no.left, value, pos);
            return no.pos > pos[0] ? no : lNo;
        } else {
            Node rNo = this.getDifNode(no.right, value, pos);
            return no.pos > pos[0] ? no : rNo;
        }
    }

    public boolean add(String value){
        if(value == "") return false;
        String binStr = charToBin(value);
        if(this.root == null){
            this.root = new Node(binStr);
            return true;
        }
        // usamos aqui o artifício de uma array com apenas um elemento
        // esse elemento representará o indice do caractere de decisão, e será modificado dentro
        // do método getDifNode, quando ele chegar em um dos nó folha.
        // na volta das chamadas recursivas, será acessado para decidir qual o nó de inserção.
        int[] decPos = {binStr.length()};
        Node no = getDifNode(this.root, binStr, decPos);
        if(no.isLeaf() && (binStr.startsWith(no.value) || no.value.startsWith(binStr))){
            return false;
        }
        Node newNo = new Node(binStr);
        Node attNo = no.clone();

        no.value = null;
        no.pos = decPos[0];
        if(binStr.charAt(no.pos) == '1'){
            no.left = attNo;
            no.right = newNo;
        } else {
            no.left = newNo;
            no.right = attNo;
        }
        return false;
    }

    private void getKeyList(Node no, List<String> wordList){
        if(no.isLeaf()){
            wordList.add(binToChar(no.value));
        } else {
            getKeyList(no.left, wordList);
            getKeyList(no.right, wordList);
        }
    }

    public List<String> getSamePref(String pref){
        List<String> wordList = new ArrayList<>();
        if(!this.isEmpty() && pref != ""){
            String binStr = charToBin(pref);
            int[] decPos = {binStr.length()};
            Node no = getDifNode(this.root, binStr, decPos);
            Node verNo = no;
            while(!verNo.isLeaf()) verNo = verNo.left;
            if(binToChar(verNo.value).startsWith(pref)){
                this.getKeyList(no, wordList);
            }
        }
        return wordList;
    }

    public Boolean contains(String value){
        if(this.root == null) return false;
        String binStr = charToBin(value);
        Node no = this.getNode(this.root, binStr);
        return no.value.equals(binStr);
    }

    public Boolean isEmpty(){
        return this.root == null;
    }

    public String toString(){
        if(this.isEmpty()) return "Esta arvore eh vazia!";
        return root.subTreeString("");
    }
}
