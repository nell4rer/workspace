package view;

import entity.Graph;
import entity.Vertex;
import method.BFS;
import method.DFS;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class GraphPanel extends JInternalFrame implements MouseMotionListener, MouseListener {

    private final static int xSize = 500;
    private final static int ySize = 500;

    private int N;

    public static BFS bfs;

    public static DFS dfs;

    private Graph graph;

    /**
     * Координаты вершин (Вершина => [x, y])
     */
    private Map<Vertex, Point> position;

    /**
     * Вершина, которую сейчас перетаскивают, иначе null
     */
    private Vertex movingVertex;

    /**
     * Вершина, из которой сейчас создается дуга, иначе null
     */
    private Vertex creatingEdgeFrom;

    /**
     * Координаты, где находится мышка, создающая новую дугу
     */
    private Point creatingEdgePosition;

    /**
     * Вершина, над которым находиться курсор, иначе null
     */
    private Vertex selectedVertex;

    /**
     * 2 вершины, между которым есть дуга с наведенной на ней курсором
     */
    private Vertex[] selectedEdge;

    /**
     * Вершина, помеченная на удаление
     */
    private Vertex vertexToDelete;

    private boolean isCircleClicked = false;


    public GraphPanel(String graphName) {
        //setVisible(true);

        //init();
        super(graphName,
                false,
                false,
                false,
                false);
        setVisible(true);
        setPreferredSize(new Dimension(xSize, ySize));
        init();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void init() {
        this.N = 0;
        //setLayout(new BorderLayout());
        this.bfs = new BFS();
        this.dfs = new DFS();
        this.graph = new Graph();
        this.position = new HashMap();

        this.movingVertex = null;
        //this.selectedVertex = null;
        this.creatingEdgeFrom = null;
        this.vertexToDelete = null;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

       // addNorthPanel();


        Vertex vertex0 = this.addVertex(new Point(50,50));
        Vertex vertex1 = this.addVertex(new Point(20,100));
        Vertex vertex2 = this.addVertex(new Point(75,100));
        Vertex vertex3 = this.addVertex(new Point(30,200));
        Vertex vertex4 = this.addVertex(new Point(120,200));
        Vertex vertex5 = this.addVertex(new Point(300,300));
//
        this.graph.setEdge(vertex0,vertex1);
        this.graph.setEdge(vertex0,vertex2);
        this.graph.setEdge(vertex1,vertex2);
        this.graph.setEdge(vertex2,vertex3);
        this.graph.setEdge(vertex2, vertex4);
        this.graph.setEdge(vertex0, vertex5);
        this.graph.setEdge(vertex3,vertex5);
        this.graph.setEdge(vertex3,vertex4);
    }

    public void paint(Graphics graphics){

        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, xSize, ySize);

        //graphics.drawRect(50,50,250,250);

        graphics.setColor(Color.black);
        for(Vertex vertex1 : this.graph.getVertices()){
            for(Vertex vertex2 : vertex1.getIncidentVertices()) {
                if ((vertex1 != this.vertexToDelete) && (vertex2 != this.vertexToDelete)) {

                    graphics.setColor(Color.black);
                    graphics.drawLine(this.position.get(vertex1).x, this.position.get(vertex1).y, this.position.get(vertex2).x, this.position.get(vertex2).y);
                    //graphics.setColor(Color.cyan);
                    graphics.setColor(Color.ORANGE);
                    graphics.fillOval(this.position.get(vertex1).x - 7, this.position.get(vertex1).y - 7, 14, 14);
                    //graphics.setColor(Color.red);
                    graphics.fillOval(this.position.get(vertex2).x - 7, this.position.get(vertex2).y - 7, 14, 14);
                    graphics.setColor(Color.red);

                }
            }
        }

        //graphics.setColor(Color.black);
        if (this.selectedEdge != null) {
            Vertex vertex1 = this.selectedEdge[0];
            Vertex vertex2 = this.selectedEdge[1];

            graphics.drawLine(this.position.get(vertex1).x + 1, this.position.get(vertex1).y, this.position.get(vertex2).x + 1, this.position.get(vertex2).y);
            graphics.drawLine(this.position.get(vertex1).x - 1, this.position.get(vertex1).y, this.position.get(vertex2).x - 1, this.position.get(vertex2).y);
            graphics.drawLine(this.position.get(vertex1).x, this.position.get(vertex1).y + 1, this.position.get(vertex2).x, this.position.get(vertex2).y + 1);
            graphics.drawLine(this.position.get(vertex1).x, this.position.get(vertex1).y - 1, this.position.get(vertex2).x, this.position.get(vertex2).y - 1);
            //graphics.setColor(Color.white);
            graphics.fillOval(this.position.get(vertex1).x - 6, this.position.get(vertex1).y - 6, 12, 12);
            graphics.fillOval(this.position.get(vertex2).x - 6, this.position.get(vertex2).y - 6, 12, 12);
            //graphics.setColor(Color.black);
        }
        //graphics.setColor(Color.red);
        if ((this.creatingEdgeFrom != null) && (this.movingVertex == null)) {
            Vertex vertex = this.underVertex(this.creatingEdgePosition);
            Point position;

            if (vertex == null) {
                position = this.creatingEdgePosition;
            } else  {
                position = this.position.get(vertex);
            }
            //graphics.setColor(Color.gray);
            graphics.drawLine(this.position.get(this.creatingEdgeFrom).x, this.position.get(this.creatingEdgeFrom).y, position.x, position.y);
            //graphics.setColor(Color.white);
            graphics.fillOval(this.position.get(this.creatingEdgeFrom).x - 6, this.position.get(this.creatingEdgeFrom).y - 6, 12, 12);

            vertex = this.underVertex(this.creatingEdgePosition);
            if (vertex == null) {
                graphics.fillOval(this.creatingEdgePosition.x - 6, this.creatingEdgePosition.y - 6, 12, 12);
            } else {
                graphics.fillOval(this.position.get(vertex).x - 6, this.position.get(vertex).y - 6, 12, 12);
            }
           // graphics.setColor(Color.black);
        }

        for (Vertex vertex : this.graph.getVertices()) {
            if (vertex != this.vertexToDelete) {
                //graphics.setColor(Color.red);
                graphics.fillOval(this.position.get(vertex).x - 4, this.position.get(vertex).y - 4, 8, 8);
            }
        }

        if ((null != this.selectedVertex) && (this.vertexToDelete != this.selectedVertex)) {
            //graphics.setColor(Color.red);
            graphics.fillOval(this.position.get(selectedVertex).x - 6, this.position.get(selectedVertex).y - 6, 12, 12);
        }

        if ((null != this.creatingEdgeFrom) && (null == this.movingVertex)) {
            Vertex vertex = this.underVertex(this.creatingEdgePosition);
            if (null == vertex) {
                //graphics.setColor(Color.gray);
                graphics.fillOval(this.creatingEdgePosition.x - 4, this.creatingEdgePosition.y - 4, 8, 8);
                //graphics.setColor(Color.black);
            }
        }

        bfs.getGraph(graph);
    }

    /**
     * Вставляет вершину
     *
     * @param point Координата x - x вершины, y - y вершины
     * @return Созданная вершина
     */
    private Vertex addVertex(Point point){
        Vertex vertex = this.graph.addVertex(N++);
        this.position.put(vertex, point);
        repaint();
        return vertex;
    }

    /**
     * Удаляет вершину
     *
     * @param Vertex Вершина для удаления
     */
    private void deleteVertex(Vertex Vertex) {
        this.position.remove(Vertex);
        this.graph.deleteVertex(Vertex);
        repaint();
    }

    /**
     * Создает дугу
     *
     * @param from Первая вершина
     * @param to   Вторая вершина
     */
    private void createEdge(Vertex from, Vertex to) {
        this.graph.setEdge(from, to);
        repaint();
    }

    /**
     * Определяет, произошло ли действие над определенной вершиной
     *
     * @param cursor Точка, над которой находиться курсор
     * @param vertex Вершина, которую проверяем
     * @return Находиться ли курсор над определенной вершиной
     */
    private boolean underVertex(Point cursor, Vertex vertex) {
        /*if (position == null) {
            return false;
        } else if (position.get(vertex) == null) {
            return false;
        } else*/ if (10 >= this.position.get(vertex).distance(cursor)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Определяет, произошло ли действие на изображении вершины
     *
     * @param cursor Точка, над которой находиться курсор
     * @return Вершина, над которой курсор, иначе null
     */
    private Vertex underVertex(Point cursor) {
        for (Vertex vertex : this.graph.getVertices()) {
            if (this.underVertex(cursor, vertex)) {
                return vertex;
            }
        }
        return null;
    }

    /**
     * Определяем, произошло ли действие на изображении определенной дуги
     *
     * @param cursor Точка, над которой находиться курсор
     * @param edge   Дуга, которую проверяем
     * @return Произошло ли действие на изображении определенной дуги
     */
    private boolean underEdge(Point cursor, Vertex[] edge) {
        Point position1 = this.position.get(edge[0]);
        Point position2 = this.position.get(edge[1]);
        double edgeLength = position1.distance(position2) - 10;
        if (edgeLength > position1.distance(cursor) & edgeLength > position2.distance(cursor)) {
            int edgeX = position1.x - position2.x;
            int edgeY = position1.y - position2.y;
            int cursorX = position1.x - cursor.x;
            int cursorY = position1.y - cursor.y;
            double edgeIncline;
            double curosorIncline;

            if (edgeX > edgeY) {
                edgeIncline = (double) edgeX / edgeY;
                curosorIncline = (double) cursorX / cursorY;
            } else {
                edgeIncline = (double) edgeY / edgeX;
                curosorIncline = (double) cursorY / cursorX;
            }
            if (0.1 > Math.abs(edgeIncline - curosorIncline)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Определяет, произошло ли действие на изображении дуг и
     *
     * @param cursor Точка, над которой находиться курсор
     * @return 2 Вершины, между которыми находиться дуга, иначе null
     */
    private Vertex[] underEdge(Point cursor) {
        Vertex[] edge = new Vertex[2];
        for (Vertex vertex1 : this.graph.getVertices()) {
            for (Vertex vertex2 : vertex1.getIncidentVertices()) {
                if (vertex1.isConnect(vertex2)) {
                    edge[0] = vertex1;
                    edge[1] = vertex2;

                    if (this.underEdge(cursor, edge)) {
                        return edge;
                    }
                }
            }
        }
        return null;
    }




    /**
     * Действия при нажатии кнопки мыши
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (null != this.movingVertex) {
            // Если выбрали место, куда переместить вершины
            this.movingVertex = null;
            this.creatingEdgeFrom = null;
        }
        else {
            if (1 == mouseEvent.getClickCount()) {
                // Если кликнули 1 раз
                Vertex[] edge = this.underEdge(mouseEvent.getPoint());
                if (edge != null) {
                    // Если по дуге - удалить ее
                    this.graph.deleteEdge(edge[0], edge[1]);
                } else {
                    Vertex result = this.underVertex(mouseEvent.getPoint());
                    if (result == null) {
                        // Если по пустому месту - создать новую вершин у
                        this.addVertex(mouseEvent.getPoint());
                    } else {
                        // Если по вершине - удалить вершину
                        this.vertexToDelete = result;
                    }
                }
            } else if (mouseEvent.getClickCount() == 2) {
                this.vertexToDelete = null;
                Vertex vertex = this.underVertex(mouseEvent.getPoint());
                if (vertex != null) {
                    this.movingVertex = vertex;
                }
            }
        }

    }

    /**
     * Действия, когда кнопку мыши нажали
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Vertex vertex = this.underVertex(mouseEvent.getPoint());
        if (vertex != null) {
            this.creatingEdgeFrom = vertex;
        }
    }

    /**
     * Действия, когда кнопку мыши отпустили
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (this.creatingEdgeFrom != null) {
            Vertex vertex = this.underVertex(mouseEvent.getPoint());
            if (vertex != this.creatingEdgeFrom) {
                if (vertex == null) {
                    vertex = this.addVertex(mouseEvent.getPoint());
                }
                this.createEdge(this.creatingEdgeFrom, vertex);
                this.creatingEdgeFrom = null;
            } else {
                this.creatingEdgeFrom = null;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    /**
     * Действия, когда кнопку мыши зажали и "тащят" какой-то объект
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (this.selectedVertex != null) {
            this.selectedVertex = null;
        }

        if (this.creatingEdgeFrom != null) {
            this.creatingEdgePosition = mouseEvent.getPoint();
            repaint();
        }


    }

    /**
     * Действия, когда курсор сдвинули с места
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        boolean needRepaint = false;

        if (this.vertexToDelete != null) {
            this.deleteVertex(this.vertexToDelete);
            this.selectedVertex = null;
        }

        if (this.movingVertex != null) {
            this.selectedVertex = null;
            this.position.get(this.movingVertex).setLocation(mouseEvent.getPoint());
            needRepaint = true;
        } else {

            if (this.selectedEdge != null) {
                if (!this.underEdge(mouseEvent.getPoint(), this.selectedEdge)) {
                    this.selectedEdge = null;

                    needRepaint = true;
                }
            }

            if (this.selectedVertex  != null) {
                if (!this.underVertex(mouseEvent.getPoint(), this.selectedVertex)) {
                    this.selectedVertex = null;
                    needRepaint = true;
                }
            }

            Vertex[] edge = this.underEdge(mouseEvent.getPoint());
            if (edge != null) {
                this.selectedEdge = edge;
                needRepaint = true;
            } else {
                Vertex vertex = this.underVertex(mouseEvent.getPoint());
                if (vertex != null) {
                    this.selectedVertex = vertex;
                    needRepaint = true;
                }
            }
        }

        if (needRepaint) {
            repaint();
        }

    }

}
