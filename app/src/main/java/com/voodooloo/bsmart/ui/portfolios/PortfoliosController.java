package com.voodooloo.bsmart.ui.portfolios;

import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.investments.Portfolio;
import com.voodooloo.bsmart.investments.PortfolioDAO;
import com.voodooloo.bsmart.ui.AppController;
import com.voodooloo.bsmart.ui.utils.Formatter;
import com.voodooloo.bsmart.ui.utils.SimpleValueFactory;
import com.voodooloo.bsmart.utils.FXMLProvider;
import com.voodooloo.bsmart.utils.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.jooq.DSLContext;
import org.pmw.tinylog.Logger;

import javax.inject.Inject;

public class PortfoliosController {
  final FXMLProvider fxmlProvider;
  final EventBus eventBus;
  final PortfolioDAO portfolioDAO;
  final Formatter formatter;

  @FXML TableView<Portfolio> portfolioTable;
  @FXML TableColumn<Portfolio, String> nameColumn;
  @FXML TableColumn<Portfolio, String> totalColumn;

  @Inject
  public PortfoliosController(FXMLProvider fxmlProvider, DSLContext context, EventBus eventBus) {
    this.fxmlProvider = fxmlProvider;
    this.eventBus = eventBus;
    portfolioDAO = new PortfolioDAO(context, eventBus);
    formatter = new Formatter();
  }

  @FXML
  public void initialize() {
    portfolioTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        ViewController<Node, SummaryController> viewController = fxmlProvider.load("layouts/portfolios/summary.fxml");
        viewController.controller.setPortfolio(newValue);
        eventBus.post(new AppController.Center(viewController));
      }
    });

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
