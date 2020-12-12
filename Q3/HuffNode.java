// Descrição no arquivo Huffman.java

public class HuffNode{

    // valor contido no nó (atualmente inteiro)
    public char value;
    // posição da cadeia de caracteres em relação as chaves
    public int priority;
    // referência ao nó filho, à diretia
    public HuffNode right;
    // referência ao nó filho, à esquerda
    public HuffNode left;

    // construtor com a posição
    public HuffNode(char value, int priority){
        this.value = value;
        this.priority = priority;
    }

    public HuffNode(HuffNode no1, HuffNode no2){
        this.left = no1;
        this.right = no2;
        this.priority = no1.priority + no2.priority;
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
    
    public HuffNode clone(){
        HuffNode newNo = new HuffNode(this.value, this.priority);
        newNo.left = this.left;
        newNo.right = this.right;
        return newNo;
    }

    // Retorna -1 0 1
    // -1 caso a prioridade de this seja menor que a de other
    // 0 caso a prioridade de this seja igual a de other
    // 1 caso a prioridade de this seja maior que a de other
    public int compare(HuffNode other){
        if(this.priority > other.priority) return 1;
        if(this.priority < other.priority) return -1;
        return 0;
    }

    public String toString(){
        String strBase = Integer.toString(this.priority);
        if(this.isLeaf()){
            strBase = "|" + strBase + "|" + Character.toString(this.value);
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