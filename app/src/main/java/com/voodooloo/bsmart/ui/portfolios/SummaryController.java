package com.voodooloo.bsmart.ui.portfolios;

import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.App;
import com.voodooloo.bsmart.investments.*;
import com.voodooloo.bsmart.ui.utils.Formatter;
import com.voodooloo.bsmart.ui.utils.SimpleValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.jooq.DSLContext;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class SummaryController {
    final App app;
    final PortfolioDAO portfolioDAO;
    final Formatter formatter;

    Portfolio portfolio;

    @FXML VBox root;

    @FXML Text nameText;
    @FXML Text valueText;
    @FXML Text firmText;

    @FXML PieChart categories;
    @FXML PieChart allocations;

    @FXML TableView<PartialHolding> summary;
    @FXML TableColumn<PartialHolding, String> nameColumn;
    @FXML TableColumn<PartialHolding, String> symbolColumn;
    @FXML TableColumn<PartialHolding, String> accountColumn;
    @FXML TableColumn<PartialHolding, String> priceColumn;
    @FXML TableColumn<PartialHolding, String> quantityColumn;
    @FXML TableColumn<PartialHolding, String> valueColumn;

    @Inject
    public SummaryController(App app, DSLContext context, EventBus bus) {
        this.app = app;
        portfolioDAO = new PortfolioDAO(context, bus);
        formatter = new Formatter();
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new SimpleValueFactory<>(partial -> partial.holding.investment.name));
        symbolColumn.setCellValueFactory(new SimpleValueFactory<>(partial -> partial.holding.investment.symbol));
        accountColumn.setCellValueFactory(new SimpleValueFactory<>(partial -> partial.holding.account.name));
        priceColumn.setCellValueFactory(new SimpleValueFactory<>(partial -> formatter.formatAsCurrency(partial.holding.investment.price)));
        quantityColumn.setCellValueFactory(new SimpleValueFactory<>(partial -> formatter.formatAsQuantity(partial.quantity())));
        valueColumn.setCellValueFactory(new SimpleValueFactory<>(partial -> formatter.formatAsCurrency(partial.value())));

        nameColumn.prefWidthProperty().bind(summary.widthProperty().multiply(.30));
        symbolColumn.prefWidthProperty().bind(summary.widthProperty().multiply(.14));
        accountColumn.prefWidthProperty().bind(summary.widthProperty().multiply(.14));
        priceColumn.prefWidthProperty().bind(summary.widthProperty().multiply(.14));
        quantityColumn.prefWidthProperty().bind(summary.widthProperty().multiply(.14));
        valueColumn.prefWidthProperty().bind(summary.widthProperty().multiply(.14));
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;

        nameText.setText(portfolio.name);
        valueText.setText(formatter.formatAsCurrency(portfolio.value()));
        firmText.setText("Investments from " + portfolio.accounts().size() + " accounts");

        ObservableList<PartialHolding> partialHoldings = FXCollections.observableArrayList(portfolio.partialHoldings);
        summary.setItems(partialHoldings);

        updateCategories();

        ObservableList<PieChart.Data> allocationsData = allocations.getData();
        allocationsData.clear();
        for (PartialHolding partialHolding : partialHoldings) {
            BigDecimal percentage = partialHolding.value().getAmount().divide(portfolio.value().getAmount(), BigDecimal.ROUND_HALF_EVEN);
            int percent = percentage.multiply(BigDecimal.valueOf(100)).intValue();
            allocationsData.add(new PieChart.Data(partialHolding.holding.investment.symbol + " (" + percent + "%)", percent));
        }
    }

    void updateCategories() {
        HashMap<Category, BigMoney> categoriesValues = new HashMap<>();

        for (PartialHolding partialHolding : portfolio.partialHoldings) {
            for (PartialCategory partialCategory : partialHolding.holding.investment.partialCategories) {
                BigMoney value = categoriesValues.get(partialCategory.category);
                if (value == null)
                {
                    value = BigMoney.zero(CurrencyUnit.USD);
                }

                value = value.plus(partialHolding.value().multipliedBy(partialCategory.percentage));
                categoriesValues.put(partialCategory.category, value);
            }
        }

        ObservableList<PieChart.Data> categoriesData = categories.getData();
        categoriesData.clear();
        for (Map.Entry<Category, BigMoney> entry : categoriesValues.entrySet()) {
            BigDecimal percentage = entry.getValue().getAmount().divide(portfolio.value().getAmount(), BigDecimal.ROUND_HALF_EVEN);
            int percent = percentage.multiply(BigDecimal.valueOf(100)).intValue();
            categoriesData.add(new PieChart.Data(entry.getKey().name + " (" + percent + "%)", percent));
        }
    }
}
