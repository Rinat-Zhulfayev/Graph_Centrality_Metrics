package graph_metrix;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws Exception {
		Graph<Integer> karate_club_graph;
		Graph<Integer> social_network_graph;
		karate_club_graph = readFileCreateGraph("karate_club_network.txt");
		social_network_graph = readFileCreateGraph("facebook_social_network.txt");


		System.out.println("2020510130 Rinat Zhulfayev:");
		System.out.println("Zachary Karate Club Network – The Highest Node for Betweennes and the value: "
				+ Centrality.vertexWithHighestBetweenness(karate_club_graph));
		System.out.println("Zachary Karate Club Network – The Highest Node for Closeness  and the value: "
				+ Centrality.vertexWithHighestCloseness(karate_club_graph));


		System.out.println("Facebook Social Network – The Highest Node for Betweennes and the value: 223 "
				+ Centrality.betweenness(social_network_graph, 223));
		System.out.println("Facebook Social Network – The Highest Node for Closeness  and the value: 528 "
				+ Centrality.closeness(social_network_graph, 528));

	}

	public static Graph<Integer> readFileCreateGraph(String textFile) throws IOException {
		BufferedReader bf;
		List<Integer> vert = new ArrayList<>();
		List<Edge> edges = new ArrayList<>();
		String[] val;
		int node_one;
		int node_two;

		try {
			bf = new BufferedReader(new FileReader(textFile));

			for (String line; (line = bf.readLine()) != null;) {
				val = line.trim().split(" ");
				node_one = Integer.parseInt(val[0]);
				node_two = Integer.parseInt(val[1]);
				edges.add(new Edge(node_one, node_two));
				if (!vert.contains(node_one))
					vert.add(node_one);
				if (!vert.contains(node_two))
					vert.add(node_two);

			}

			bf.close();
		} catch (FileNotFoundException e) {
			System.out.println("No such a file");
			e.printStackTrace();
		}

		Graph<Integer> graph = new Graph<Integer>(vert, edges);
		return graph;
	}

}
