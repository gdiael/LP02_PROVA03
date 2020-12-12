// Descrições no arquivo Huffman.java

public class Heap {
	
	private HuffNode[] elements;
	private int size;
	private int capacity;

	public Heap(int capacity) {
		size = 0;
		this.capacity = capacity;
		elements = new HuffNode[capacity];
	}

	public Heap(){
		this(100);
	}

	public Heap(HuffNode[] vector){
		if(vector.length > 0){
			this.size = vector.length;
			this.capacity = vector.length;
			this.elements = vector;
			for(int i = this.size/2-1; i>=0; i--){
				descer(i);
			}
		}else{
			this.size = 0;
			this.capacity = 100;
			this.elements = new HuffNode[this.capacity];
		}
	}
	
	// Verifica se o vetor base está vazio
	public Boolean isEmpty(){
		return this.size < 1;
	}

	// Verifica se o vetor base está vazio e joga uma mensagem de erro
	private Boolean isEmptyWithWarning(){
		if(this.isEmpty()){
			throw new IndexOutOfBoundsException("Esta lista está vazia!");
		}
		return false;
	}

	// verifica se o elemento e seu pai atendem a condição de heap, se não, sobe este elemento
	private void subir(int i){
		if(this.isEmptyWithWarning()) return;

		int parent = (i+1)/2-1;
		if(parent < 0) return; // o inice informado representa o primeiro elemento, por isso não tem pai
		if(parent >= this.size) return; // caso o indice informado esteja muito fora, quando dividirmos por dois ainda estará fora
		if(this.elements[i].compare(this.elements[parent]) < 0){ // se o elemento atual for menor que sei pai
			this.trocar(i, parent); // trocamos os dois de posição
			this.subir(parent); // fazemos a verifcação na nova posição
		}
	}
	
	// verifica se o elemento e seus filhos atendem a condição de heap, se não, desce este elemento
	private void descer(int i){
		if(this.isEmptyWithWarning()) return;

		int son = (i+1)*2-1; // sera o indice do primeiro filho
		if(son >= this.size) return; // se o indice em son está fora do vetor, nada a fazer
		if(son < this.size-1){ // se existe um proximo elemento
			if(this.elements[son+1].compare(this.elements[son]) < 0){ // verificamos se ele é menor que son
				son++; // se for incrementamos son para o proximo indice
			}
		}
		if(this.elements[son].compare(this.elements[i]) < 0){ // se o elemento em son é menor que o atual
			this.trocar(i, son); // trocamos de lugar
			descer(son); // procedemos com a verificação na nova posição
		}
	}

	// troca dois elementos de posição
	private void trocar(int a, int b){
		if(this.isEmptyWithWarning()) return;

		HuffNode aux = this.elements[a];
		this.elements[a] = this.elements[b];
		this.elements[b] = aux;
	}

	public int getSize(){
		return this.size;
	}

	private void resize(){
		HuffNode[] newVector = new HuffNode[this.size*2];
		for(int i=0; i < this.size; i++){
			newVector[i] = this.elements[i];
		}
		this.elements = newVector;
		this.capacity = this.elements.length;
	}

	public boolean add(HuffNode value) {
		if(this.size == this.capacity){
			this.resize();
		}
		this.elements[this.size] = value;
		this.size += 1;
		subir(this.size-1);
		return true;
	}
	
	// retorna o maior elemento [primeiro], remove-o e reorganiza o vetor para continuar nas condições de heap
	public HuffNode remove() {
		if(this.isEmptyWithWarning()) return null;

		HuffNode valor = this.elements[0];
		this.size -= 1;
		if(this.size != 0){
			this.trocar(0, this.size);
			this.descer(0);
		}
		return valor;
	}
	
	// retorna o primeiro elemento
	public HuffNode get() {
		if(this.isEmptyWithWarning()) return null;

		return this.elements[0];
	}
	
	// atualiza uma determinada prioridade
	public void update(int priority, int newPriority) {
		if(this.isEmptyWithWarning()) return;

		Boolean noFind = true;
		for(int i = 0;i < this.size;i++){
			if(this.elements[i].priority == priority){
				this.elements[i].priority = newPriority;
				if(newPriority < priority){
					this.subir(i);
				}
				if(newPriority > priority){
					this.descer(i);
				}
				noFind = false;
				break;
			}
		}
		if(noFind) System.out.println("Valor nao encontrado!");
	}

	// Converte os valores da lista para string
	public String toString(){
		String saida = "";
		for(int i=0; i<this.size; i++){
			saida += this.elements[i] + " ";
		}
		return saida;
	}
}