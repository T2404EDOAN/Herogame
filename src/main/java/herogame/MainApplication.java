package herogame;

import herogame.dao.NationalDAO;
import herogame.dao.PlayerDAO;
import herogame.model.National;
import herogame.model.Player;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApplication extends Application {
    private PlayerDAO playerDAO = new PlayerDAO();
    private NationalDAO nationalDAO = new NationalDAO();
    private TableView<Player> playerTable = new TableView<>();
    private ComboBox<National> nationalComboBox = new ComboBox<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hero Game Management");

        // Create UI components
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));

        // Player Form
        GridPane playerForm = new GridPane();
        playerForm.setHgap(10);
        playerForm.setVgap(5);

        TextField nameField = new TextField();
        TextField scoreField = new TextField();
        TextField levelField = new TextField();

        playerForm.addRow(0, new Label("Player Name:"), nameField);
        playerForm.addRow(1, new Label("High Score:"), scoreField);
        playerForm.addRow(2, new Label("Level:"), levelField);
        playerForm.addRow(3, new Label("National:"), nationalComboBox);

        // Buttons
        HBox buttonBox = new HBox(10);
        Button addButton = new Button("Add Player");
        Button deleteButton = new Button("Delete Player");
        Button searchButton = new Button("Search");
        Button top10Button = new Button("Show Top 10");
        TextField searchField = new TextField();
        searchField.setPromptText("Search by name...");

        buttonBox.getChildren().addAll(addButton, deleteButton, searchField, searchButton, top10Button);

        // Table
        TableColumn<Player, Integer> idCol = new TableColumn<>("Player ID");
        TableColumn<Player, String> nameCol = new TableColumn<>("Player Name");
        TableColumn<Player, Integer> scoreCol = new TableColumn<>("High Score");
        TableColumn<Player, Integer> levelCol = new TableColumn<>("Level");
        TableColumn<Player, String> nationalCol = new TableColumn<>("National");

        idCol.setCellValueFactory(new PropertyValueFactory<>("playerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("highScore"));
        levelCol.setCellValueFactory(new PropertyValueFactory<>("level"));
        nationalCol.setCellValueFactory(new PropertyValueFactory<>("nationalName"));

        playerTable.getColumns().addAll(idCol, nameCol, scoreCol, levelCol, nationalCol);

        // Add components to main layout
        mainLayout.getChildren().addAll(playerForm, buttonBox, playerTable);

        // Load data
        refreshNationalComboBox();
        refreshPlayerTable();

        // Event handlers
        addButton.setOnAction(e -> {
            try {
                Player player = new Player();
                player.setPlayerName(nameField.getText());
                player.setHighScore(Integer.parseInt(scoreField.getText()));
                player.setLevel(Integer.parseInt(levelField.getText()));
                player.setNationalId(nationalComboBox.getValue().getNationalId());

                playerDAO.addPlayer(player);
                refreshPlayerTable();
                clearFields(nameField, scoreField, levelField);
            } catch (Exception ex) {
                showAlert("Lỗi", "Vui lòng nhập dữ liệu hợp lệ!");
            }
        });

        deleteButton.setOnAction(e -> {
            Player selectedPlayer = playerTable.getSelectionModel().getSelectedItem();
            if (selectedPlayer != null) {
                playerDAO.deletePlayer(selectedPlayer.getPlayerId());
                refreshPlayerTable();
            } else {
                showAlert("Lỗi", "Vui lòng chọn 1 người để xóa.");
            }
        });

        searchButton.setOnAction(e -> {
            String searchText = searchField.getText();
            playerTable.setItems(FXCollections.observableArrayList(
                    playerDAO.findPlayersByName(searchText)
            ));
        });

        top10Button.setOnAction(e -> {
            playerTable.setItems(FXCollections.observableArrayList(
                    playerDAO.getTop10Players()
            ));
        });

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void refreshPlayerTable() {
        playerTable.setItems(FXCollections.observableArrayList(
                playerDAO.getAllPlayers()
        ));
    }

    private void refreshNationalComboBox() {
        nationalComboBox.setItems(FXCollections.observableArrayList(
                nationalDAO.getAllNationals()
        ));
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}