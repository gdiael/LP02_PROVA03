// Q3
// Foi reutilizado o código da Lista 04 para a lista de prioridade
// o Algoritmo seguiu estes passos:
// - criação de uma dicionário de frequencia relacionado a cada caractere do texto fornecido
// - criação de um vetor de nós, onde cada nó representa um caractere e sua respectiva frequencia
// - preechimento do heap com esse vetor (o heap foi invertido, ou seja é um minHeap);
// - interagir no heap tranformando cada par de saídas em dois nós filhos de um novo nó pai
// - marcar o ultimo nó restante após a interação como nó raiz
// - preenchimento do dicionário de códigos com os valores binários montados de acordo com o formado de árvore.
// - no método Main é mostrado um texto, usado para gerar a arvore de huffman e após isso é feita sua codificação
// - e decodificação.
// PONTOS EXTRAS - Comecei a trabalhar num método para compactar e descompactar a arvore, mas infelizmente não deu
//                 tempo de finalizar, por isso retirei dessa versão do cédigo.
//                 A idéia era a seguinte: contar o número de nós, converter esse número para uma string binária
//                 em seguida percorrer a arvore, quando fosse encontrado um nó folha, seria adicionado '1' na
//                 string e em seguinda o binário representando o caractere, quando encontra-se um nó de decisão
//                 seria adicionado '0' a string, e em seguida os dados do filho esquerda e a direita, de forma
//                 recursiva seguindo o anterior, assim no final teriamos uma string binária representando a arvore
//                 ao final dessa string seria concatenada a string binária do texto códificado.
//                 Para salvar essa string binário num arquivo binário seria usado o tipo BigInteger, para converter
//                 a string binária num array de bytes.
//                 Para a descompactação seria feito o caminho inverso, o arquivo binário seria lido e convertido 
//                 numa string binária usando o BigInteger, no inicio desse arquivo está nosso número de nós
//                 esse número será usado para percorrer a string binária recriando os nós da arvore, ao encontrar
//                 '0' sabemos que é um nó de decisão, ao encontrar '1' sabemos que é um nó folha seguindo de seu
//                 caractere. Ao chegar no número de nós, saber que o restante é o texto códificado.

import java.util.HashMap;
import java.util.Map;

public class Huffman {
	private HuffNode root;
	private Map<Character, String> codes;

	public static void main(String[] args) {
		System.out.println("");
		System.out.println("Questao 03 - Prova 03");
		String text = "Linguagem de programacao II";
		Huffman codeTree = Huffman.fromText(text);
		System.out.println("Imprimindo a arvore de Huffman");
		System.out.println(codeTree);
		String binText = codeTree.encodeText(text);
		System.out.println("Texto comprimido: " + binText);
		System.out.println("Texto descomprimido: " + codeTree.decodeText(binText));
		
	}

	public Huffman(){
	}

	// Verifica se a arvore está vazia
	private Boolean isEmpty(){
		return this.root == null;
	}

	private static Map<Character, Integer> charFrequency(String text){
		Map<Character, Integer> charDict = new HashMap<>();
		for(int i = 0; i < text.length(); i++){
			Character ch = text.charAt(i);
			if(charDict.containsKey(ch)){
				charDict.put(ch, charDict.get(ch)+1);
			} else {
				charDict.put(ch, 1);
			}
		}
		return charDict;
	}

	private void updateCodes(HuffNode no, String code){
		if(no.isLeaf()){
			this.codes.put(no.value, code);
		} else {
			updateCodes(no.left, code + "0");
			updateCodes(no.right, code + "1");
		}
	}

	public static Huffman fromText(String text){
		Huffman codeTree = new Huffman();
		Map<Character, Integer> charDict = charFrequency(text);
		HuffNode[] nodeList = new HuffNode[charDict.size()];
		int i = 0;
		for (Character ch : charDict.keySet()) {
			nodeList[i] = new HuffNode(ch, charDict.get(ch));
			i += 1;
		}
		Heap priList = new Heap(nodeList);
		while(priList.getSize() > 1){
			HuffNode left = priList.remove();
			HuffNode right = priList.remove();
			HuffNode top = new HuffNode(left, right);
			priList.add(top);
		}
		codeTree.root = priList.remove();
		codeTree.codes = new HashMap<>();
		codeTree.updateCodes(codeTree.root, "");
		return codeTree;
	}

	public String getCode(Character key){
		return this.codes.get(key);
	}

	public String encodeText(String text){
		String binOut = "";
		for(int i = 0; i < text.length(); i++){
			char ch = text.charAt(i);
			binOut += getCode(ch);
		}
		return binOut;
	}

	public String decodeText(String binStr){
		String strOut = "";
		HuffNode no = this.root;
		int pos = 0;
		while(pos < binStr.length()){
			no = binStr.charAt(pos) == '0' ? no.left : no.right;
			if(no.isLeaf()){
				strOut += no.value;
				no = this.root;
			}
			pos++;
		}
		// if(no.isLeaf()) strOut += no.value;
		return strOut;
	}

	// Converte os valores da lista para string
	public String toString(){
		if(this.isEmpty()) return "Esta arvore eh vazia!";
        return root.subTreeString("");
	}
}