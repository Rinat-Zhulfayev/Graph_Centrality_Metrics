package graph_metrix;

import java.util.*;

public class Graph<V> {
	protected List<V> vertices = new ArrayList<>();
	protected List<Integer>[] adjList;
	protected int max_numOfNode;

	protected Graph(List<V> vertices, List<Edge> edges) {
		for (int i = 0; i < vertices.size(); i++) {
			addVertex(vertices.get(i));
		}
		initAdjList();
		for (int i = 0; i < edges.size(); i++) {
			if (vertices.contains(edges.get(i).first_vertex) && vertices.contains(edges.get(i).second_vertex)) {
				addEdge(edges.get(i).first_vertex, edges.get(i).second_vertex);
			}

		}
	}

	private void initAdjList() {
		max_numOfNode = Collections.max((Collection<Integer>) vertices);
		adjList = new ArrayList[Size() + 1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<>();
		}
	}

	int Size() {
		return vertices.size();
	}

	public List<V> getVertices() {
		return vertices;
	}

	public V getVertex(int index) {
		return vertices.get(index);
	}

	public List<Integer>[] getAdj() {
		return adjList;
	}

	public int getIndex(V v) {
		return vertices.indexOf(v);
	}

	public void printEdges() {
		for (int i = 0; i < max_numOfNode; i++) {
			System.out.print(" (" + i + ") " + getVertex(i) + ": ");
			for (int j = 0; j < adjList[(int) getVertex(i)].size(); j++) {
				System.out.print(adjList[(int) getVertex(i)].get(j) + "  ");
			}
			System.out.println();
		}
	}

	public void clear() {
		vertices.clear();
	}

	public boolean addVertex(V vertex) {
		if (!vertices.contains(vertex)) {
			vertices.add(vertex);
			return true;
		} else
			return false;
	}

	public void addEdge(int u, int v) {
		if (u < 0 || u > max_numOfNode)
			throw new IllegalArgumentException("No such index: " + u);
		if (v < 0 || v > max_numOfNode)
			throw new IllegalArgumentException("No such index: " + v);

		if (!adjList[u].contains(v)) {
			adjList[u].add(v);
		}
		if (!adjList[v].contains(u)) {
			adjList[v].add(u);
		}

	}

	static void searchPaths(ArrayList<ArrayList<Integer>> paths, ArrayList<Integer> path,
			ArrayList<ArrayList<Integer>> parent, int size, int end) {

		if (end == -1) {
			paths.add(new ArrayList<>(path));
			return;
		}

		for (int par : parent.get(end)) {
			path.add(end);
			searchPaths(paths, path, parent, size, par);
			path.remove(path.size() - 1);
		}
	}

	static void bfs(List<Integer>[] adj, ArrayList<ArrayList<Integer>> parent, int size, int start) {

		// dist will contain shortest distance from start to every other vertex
		int[] dist = new int[size];
		Arrays.fill(dist, Integer.MAX_VALUE);

		Queue<Integer> queue = new LinkedList<>();

		queue.offer(start);

		parent.get(start).clear();
		parent.get(start).add(-1);

		dist[start] = 0;

		while (!queue.isEmpty()) {
			int u = queue.poll();

			for (int v : adj[u]) {
				if (dist[v] > dist[u] + 1) {
					dist[v] = dist[u] + 1;
					queue.offer(v);
					parent.get(v).clear();
					parent.get(v).add(u);
				} else if (dist[v] == dist[u] + 1) {
					parent.get(v).add(u);
				}
			}
		}
	}

	public ArrayList<ArrayList<Integer>> fillParent(List<Integer>[] adj, int size, int node) {
		ArrayList<ArrayList<Integer>> parent = new ArrayList<>();
		for (int i = 0; i < size +1; i++) {
			parent.add(new ArrayList<>());
		}
			bfs(adj, parent, size + 1, node);
			return parent;
	}
	public List<Integer> get_paths(List<Integer>[] adj, int size, int start, int end, int mid, ArrayList<ArrayList<Integer>> parent) {
		ArrayList<ArrayList<Integer>> paths = new ArrayList<>(); // list off all shortest paths between 2 node
		ArrayList<Integer> path = new ArrayList<>();
		List<Integer> values = new ArrayList<>(); // holds values for calculations
		int through = 0; // number of shrtst paths that goes through mid node

		searchPaths(paths, path, parent, size + 1, end);

		for (ArrayList<Integer> v : paths) {
			Collections.reverse(v);
			if (v.contains(mid))
				through++;
		}

		values.add(through);// num of paths contains given vertex(for betweenness)
		values.add(paths.size());// num off all pathes(for betweenness)
		if (!paths.isEmpty())
			values.add(paths.get(0).size() - 1);// num of the edges in shortest path between 2 nodes(for
												// closeness)
		else
			values.add(0);
		return values;
	}

	public Boolean isReachable(int start, int end) {

		boolean visited[] = new boolean[Size() + 1];

		LinkedList<Integer> queue = new LinkedList<Integer>();

		visited[start] = true;
		queue.add(start);

		Iterator<Integer> i;
		while (queue.size() != 0) {
			start = queue.poll();
			int n;
			i = adjList[start].listIterator();

			while (i.hasNext()) {
				n = i.next();

				if (n == end)
					return true;

				if (!visited[n]) {
					visited[n] = true;
					queue.add(n);
				}
			}
		}
		return false;
	}


	// can also be used for calculations, but takes more time for execution. Finds
	// all shortest paths using DFS
	public List<Integer> valuesForCalc(List<List<Integer>> paths, int node) {
		List<Integer> values = new ArrayList<>();
		values.add(0);
		values.add(0);
		values.add(0);
		int through = 0;
		for (int i = 0; i < paths.size(); i++) {
			if (paths.get(i).contains(node))
				through++;
		}
		if (!paths.isEmpty()) {
			values.set(0, through);
			values.set(1, paths.size());
			values.set(2, paths.get(0).size() - 1);
		}
		return values;
	}

	public List<List<Integer>> findAllShrtstPaths(int start, int end) {
		List<List<Integer>> paths = new ArrayList<>();
		boolean[] visited = new boolean[Size() + 1];
		List<Integer> currentPath = new ArrayList<>();
		currentPath.add(start);
		dfs(start, end, visited, currentPath, paths);
		return paths;
	}

	private void dfs(int start, int end, boolean[] visited, List<Integer> current, List<List<Integer>> paths) {
		visited[start] = true;

		if (start == end) {
			System.out.println(current);

			if (paths.isEmpty()) // returns only shortest path or paths if more than one exist
				paths.add(new ArrayList<Integer>(current));
			else if (paths.get(0).size() > current.size()) {
				paths.clear();
				paths.add(new ArrayList<Integer>(current));
			} else if (paths.get(0).size() == current.size())
				paths.add(new ArrayList<Integer>(current));

		}

		for (Integer i : adjList[start]) {
			if (!visited[i]) {
				current.add(i);
				dfs(i, end, visited, current, paths);
				current.remove(i);
			}
		}
		visited[start] = false;
	}
	
}
