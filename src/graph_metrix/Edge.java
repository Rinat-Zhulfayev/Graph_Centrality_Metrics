package graph_metrix;

public class Edge {
	int first_vertex, second_vertex;

	public Edge(int first_vertex, int second_vertex) {
		this.first_vertex = first_vertex;
		this.second_vertex = second_vertex;
	}
	
	public int getFirst_vertex() {
		return first_vertex;
	}

	public int getSecond_vertex() {
		return second_vertex;
	}

	public boolean equals(Object o) {
		return first_vertex == ((Edge)o).first_vertex && second_vertex == ((Edge)o).second_vertex;
	}

}
