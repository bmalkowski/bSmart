package com.voodooloo.bsmart.ui.accounts;

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
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.jooq.DSLContext;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class SummaryController {
    final App app;
    final AccountDAO accountDAO;
    final Formatter formatter;

    Account account;

    @FXML VBox root;

    @FXML PieChart categories;
    @FXML PieChart allocations;

    @FXML TableView<Holding> summary;
    @FXML TableColumn<Holding, String> nameColumn;
    @FXML TableColumn<Holding, String> symbolColumn;
    @FXML TableColumn<Holding, String> priceColumn;
    @FXML TableColumn<Holding, String> quantityColumn;
    @FXML TableColumn<Holding, String> valueColumn;

    @Inject
    public SummaryController(App app, DSLContext context, EventBus bus) {
        this.app = app;
        accountDAO = new AccountDAO(context, bus);
        formatter = new Formatter();
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new SimpleValueFactory<>(holding -> holding.investment.name));
        symbolColumn.setCellValueFactory(new SimpleValueFactory<>(holding -> holding.investment.symbol));
        priceColumn.setCellValueFactory(new SimpleValueFactory<>(holding -> formatter.formatAsCurrency(holding.investment.price)));
        quantityColumn.setCellValueFactory(new SimpleValueFactory<>(holding -> formatter.formatAsQuantity(holding.quantity)));
        valueColumn.setCellValueFactory(new SimpleValueFactory<>(holding -> formatter.formatAsCurrency(holding.value())));

        nameColumn.prefWidthProperty().bind(summary.widthProperty().multiply(.40));
        symbolColumn.prefWidthProperty().bind(summary.widthProperty().multiply(.15));
        priceColumn.prefWidthProperty().bind(summary.widthProperty().multiply(.15));
        quantityColumn.prefWidthProperty().bind(summary.widthProperty().multiply(.15));
        valueColumn.prefWidthProperty().bind(summary.widthProperty().multiply(.15));
    }

    public void setAccount(Account account) {
        this.account = account;

        ObservableList<Holding> holdings = FXCollections.observableArrayList(account.holdings);
        summary.setItems(holdings);

        updateCategories();

        ObservableList<PieChart.Data> allocationsData = allocations.getData();
        allocationsData.clear();
        for (Holding holding : holdings) {
            BigDecimal percentage = holding.value().getAmount().divide(account.value().getAmount(), BigDecimal.ROUND_HALF_EVEN);
            int percent = percentage.multiply(BigDecimal.valueOf(100)).intValue();
            allocationsData.add(new PieChart.Data(holding.investment.symbol + " (" + percent + "%)", percent));
        }
    }

    void updateCategories() {
        BigMoney accountValue = account.value();
        HashMap<Category, BigMoney> categoriesValues = new HashMap<>();

        for (Holding holding : account.holdings) {
            for (PartialCategory partialCategory : holding.investment.partialCategories) {
                BigMoney value = categoriesValues.get(partialCategory.category);
                if (value == null)
                {
                    value = BigMoney.zero(CurrencyUnit.USD);
                }

                value = value.plus(holding.value().multipliedBy(partialCategory.percentage));
                categoriesValues.put(partialCategory.category, value);
            }
        }

        ObservableList<PieChart.Data> categoriesData = categories.getData();
        categoriesData.clear();
        for (Map.Entry<Category, BigMoney> entry : categoriesValues.entrySet()) {
            BigDecimal percentage = entry.getValue().getAmount().divide(accountValue.getAmount(), BigDecimal.ROUND_HALF_EVEN);
            int percent = percentage.multiply(BigDecimal.valueOf(100)).intValue();
            categoriesData.add(new PieChart.Data(entry.getKey().name + " (" + percent + "%)", percent));
        }
    }
}
