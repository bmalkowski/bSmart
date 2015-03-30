package com.voodooloo.bsmart.ui.portfolios;

import com.voodooloo.bsmart.investments.Portfolio;
import com.voodooloo.bsmart.investments.PortfolioDAO;
import com.voodooloo.bsmart.ui.utils.Formatter;
import com.voodooloo.bsmart.ui.utils.SimpleValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.pmw.tinylog.Logger;

import javax.inject.Inject;

public class PortfoliosController {
  final PortfolioDAO portfolioDAO;
  final Formatter formatter;

  @FXML TableView<Portfolio> portfolioTable;
  @FXML TableColumn<Portfolio, String> nameColumn;
  @FXML TableColumn<Portfolio, String> totalColumn;

  @Inject
  public PortfoliosController(PortfolioDAO portfolioDAO) {
    this.portfolioDAO = portfolioDAO;
    formatter = new Formatter();
  }

  @FXML
  public void initialize() {
    nameColumn.setCellValueFactory(new SimpleValueFactory<>(portfolio -> portfolio.name));
    totalColumn.setCellValueFactory(new SimpleValueFactory<>(portfolio -> formatter.formatAsCurrency(portfolio.value())));
    updatePortfolios();
  }

  void updatePortfolios() {
    try {
      ObservableList<Portfolio> portfolios = FXCollections.observableArrayList(portfolioDAO.findAll());
      portfolioTable.setItems(portfolios);
    }
    catch (Exception e) {
      Logger.error(e);
    }
  }
}
