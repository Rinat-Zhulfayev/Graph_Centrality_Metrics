package graph_metrix;

import java.util.ArrayList;
import java.util.List;

public class Centrality {

	public static double betweenness(Graph<Integer> graph, int node) throws Exception {
		if (!graph.getVertices().contains(node)) {
			throw new Exception("No such vertex in the graph! Betweenness undefined");
		}
		List<Integer> vertices = graph.getVertices();
		double result = 0;
		double total = 0;
		double through = 0;

		List<Integer> list = new ArrayList<>();

		for (int i = 0; i < vertices.size(); i++) {
			ArrayList<ArrayList<Integer>> parent = graph.fillParent(graph.adjList, graph.Size(), vertices.get(i));
			for (int j = i + 1; j < vertices.size(); j++) { // control all pairs of nodes only once
				if (vertices.get(i) != node && vertices.get(j) != node) {
					list = graph.get_paths(graph.getAdj(), graph.max_numOfNode, vertices.get(i), vertices.get(j), node, parent);
					total = list.get(1);
					through = list.get(0);
					if (total != 0)
						result += through / total;
					total = 0;
					through = 0;
					
				}
			}
		}
		return result;
	}


	public static double closeness(Graph<Integer> graph, int node) throws Exception {
		if (!graph.getVertices().contains(node)) {
			throw new Exception("No such vertex in the graph! Closeness undefined");
		}
		List<Integer> vertices = graph.getVertices();
		double result = 0;
		double numOfNodes = vertices.size();
		double distance = 0;

		List<Integer> list = new ArrayList<>();

		for (int i = 0; i < numOfNodes; i++) {
			if (vertices.get(i) != node) {
				ArrayList<ArrayList<Integer>> parent = graph.fillParent(graph.adjList, graph.Size(), vertices.get(i));
				list = graph.get_paths(graph.getAdj(), graph.max_numOfNode, vertices.get(i), node, 0, parent);

				if (!list.isEmpty()) {
					if (list.get(1) != 0) {
						distance += list.get(2); // list contains length of the path

					} else
						numOfNodes--; // don't involve not connected nodes to calculation
				}

			}
		}
		if (distance == 0) {
			result = 0;
		} else
			result = (numOfNodes - 1) / distance; // 1/avg of the shortest paths

		return result;

	}

	public static String vertexWithHighestBetweenness(Graph<Integer> graph) throws Exception {
		double value = 0;
		String vertex = "";
		double temp;
		for (int i = 0; i < graph.getVertices().size(); i++) {
			temp = betweenness(graph, graph.getVertex(i));
			if (value < temp) {
				value = temp;
				vertex = graph.getVertex(i).toString();
		        
			}
//			if (value == temp) {
//				vertex = vertex + ", " + graph.getVertex(i).toString();
//			}
		}
		return (vertex + " " + value);
	}

	public static String vertexWithHighestCloseness(Graph<Integer> graph) throws Exception {
		double value = 0;
		String vertex = "";
		double temp;
		for (int i = 0; i < graph.getVertices().size(); i++) {
			temp = closeness(graph, graph.getVertex(i));
			if (value < temp) {
				value = temp;
				vertex = graph.getVertex(i).toString();
			}
//			if (value == temp) {
//				vertex = vertex + ", " + graph.getVertex(i).toString();
//			}
		}
		return (vertex + " " + value);
	}

}
