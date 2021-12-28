import Vue from 'vue';
import Vuex from 'vuex';
import axios from "axios";

Vue.use(Vuex);

// const API_BASE = 'https://finance.perryandalex.com/api/v1/';
const API_BASE = 'http://localhost:8090/api/v1/';

export default new Vuex.Store({
    state: {
        user: {},
        users: [],
        token: localStorage.getItem('token') || '',
        isLoggedIn : false,
        budgetItems: [],
        budgetTypes: [],
        accountingPeriods: [],
        accounts: [],
        accountTypes: [],
        accountingPeriodMonths: [],
        budgetItemsByMonth: [],
        savingsGoals: [],
        periodBudgets: [],
        stockPurchases: [],
        accountHistories: [],
    },
    mutations: {
        setUser(state, user) {
            state.user = user;
        },
        assignUsers(state, users) {
            state.users = users;
        },
        newUser(state, user) {
            state.users.push(user);
        },
        auth(state, token) {
            state.token = token;
        },
        setIsLoggedIn(state, isLoggedIn) {
            state.isLoggedIn = isLoggedIn;
        },
        setBudgetItems(state, budgetItems) {
            state.budgetItems = budgetItems;
        },
        setBudgetTypes(state, budgetTypes) {
            state.budgetTypes = budgetTypes;
        },
        setAccountingPeriods(state, accountingPeriods) {
            state.accountingPeriods = accountingPeriods;
        },
        setAccounts(state, accounts) {
            state.accounts = accounts;
        },
        updateAccounts(state, account) {
            state.accounts = state.accounts.push(account);
        },
        setStockPurchases(state, stockPurchases) {
            state.stockPurchases = stockPurchases;
        },
        setAccountHistories(state, accountHistories) {
            state.accountHistories = accountHistories;
        },
        setAccountTypes(state, accountTypes) {
            state.accountTypes = accountTypes;
        },
        setAccountingPeriodMonths(state, accountingPeriodMonths) {
            state.accountingPeriodMonths = accountingPeriodMonths;
        },
        setSavingsGoals(state, savingsGoals) {
            state.savingsGoals = savingsGoals;
        },
        setPeriodBudgets(state, periodBudgets) {
            state.periodBudgets = periodBudgets;
        },
        buildBudgetItemsByAccountingMonth(state, months) { //this call must be made after fetchBudgetItems has resolved
            if (months.length > 0 && state.budgetItems.length > 0) {
                //TODO could be optomized by sorting

                //inserting a budget item to its proper position in months array based on accountingPeriod
                for (let i = 0; i < state.budgetItems.length; i++) {
                    const budgetItemAccountingPeriodId = state.budgetItems[i].accountingPeriod.accountingPeriodId;

                    for (let y = 0; y < months.length; y++) {
                        let foundAccountingPeriod = false;

                        for (let z = 0; z < months[y].accountingPeriods.length; z++) {
                            if (budgetItemAccountingPeriodId === months[y].accountingPeriods[z].accountingPeriodId) {
                                if (typeof months[y].accountingPeriods[z].budgetItems !== 'undefined') {
                                    months[y].accountingPeriods[z].budgetItems.push(state.budgetItems[i]);
                                }
                                else {
                                    months[y].accountingPeriods[z].budgetItems = [state.budgetItems[i]];
                                }
                                foundAccountingPeriod = true;
                                break;
                            }
                        }
                        if (foundAccountingPeriod) {
                            break;
                        }
                    }
                }
            }
            state.budgetItemsByMonth = months;
            console.log(state);
        },
    },
    actions: { //can be used to perform async requests
        setUsers({ commit }) {
            axios
                .get(API_BASE + "users")
                .then((res) => {
                    commit('assignUsers', res.data);
                    console.log(this);
                    console.log(this.getters.allusers);


                    //test display in card-----------------------------------
                    let itemTemps = [];
                    for (let i = 0; i < res.data.length; i++) {
                        itemTemps[i] = {
                            avatar: "https://cdn.vuetifyjs.com/images/lists/5.jpg",
                            title: res.data[i].name,
                            subtitle: res.data[i].email,
                        };
                    }
                    // commit('assignItems', itemTemps);
                    console.log('setUsers finished');
                    //test display in card-----------------------------------

                })
                .catch((err) => {
                    console.log(err);
                });
        },
        getUser({ commit }, id) { //this.$store.dispatch('getUser', 1);
            axios
                .get(API_BASE + `users/${id}`)
                .then((res) => {
                    console.log(commit);
                    console.log(res.data);

                })
                .catch((err) => {
                    console.log(err);
                });
        },
        getUserByToken({ commit }) { //this.$store.dispatch('getUser', 1);
            axios
                .get(API_BASE + 'user')
                .then((res) => {
                    console.log(commit);
                    console.log(res.data);
                    commit("setUser", res.data);

                })
                .catch((err) => {
                    console.log(err);
                });
        },
        fetchBudgetItems({ commit }, payload) {
            const id = payload.id;
            const year = payload.year;

            return new Promise((resolve, reject) => {
                axios
                    .get(API_BASE + `budget_items/user/${id}/${year}`)
                    .then((res) => {
                        console.log(commit);
                        console.log(res.data);
                        commit("setBudgetItems", res.data);
                        resolve();

                    })
                    .catch((err) => {
                        console.log(err);
                        reject();
                    });
            })
        },
        updateBudgetItem({ commit }, updatedBudgetItem) {
            return new Promise((resolve, reject) => {
                console.log('updatedBudgetItem');
                console.log(updatedBudgetItem);
                axios.put(API_BASE + `budget_items/${updatedBudgetItem.budgetItemId}`, updatedBudgetItem)
                    .then(function (res) {
                        console.log(commit);
                        console.log(res.data);
                        resolve();
                    })
                    .catch(function (err) {
                        console.log(err);
                        reject();
                    });
            })
        },
        deleteBudgetItem({ commit }, deletedBudgetItem) {
            return new Promise((resolve, reject) => {
                console.log('deletedBudgetItem');
                console.log(deletedBudgetItem);
                axios.delete(API_BASE + `budget_items/${deletedBudgetItem.budgetItemId}`, deletedBudgetItem)
                    .then(function (res) {
                        console.log(commit);
                        console.log(res.data);
                        resolve();
                    })
                    .catch(function (err) {
                        console.log(err);
                        reject();
                    });
            })
        },
        createBudgetItem({ commit }, newBudgetItem) {
            return new Promise((resolve, reject) => {
                axios.post(API_BASE + 'budget_items', newBudgetItem)
                    .then(function (res) {
                        // console.log(commit);
                        // console.log(res.data);
                        console.log(commit);
                        console.log(res.data);
                        resolve();
                    })
                    .catch(function (err) {
                        console.log(err);
                        reject();
                    });
            })
        },
        fetchSavingsGoals({ commit }) {
            return new Promise((resolve, reject) => {
                axios
                    .get(API_BASE + `savings_goals`)
                    .then((res) => {
                        console.log(commit);
                        console.log(res.data);
                        commit("setSavingsGoals", res.data);
                        resolve();

                    })
                    .catch((err) => {
                        console.log(err);
                        reject();
                    });
            })
        },
        fetchPeriodBudgets({ commit }, year) {
            return new Promise((resolve, reject) => {
                axios
                    .get(API_BASE + `period_budgets/year/${year}`)
                    .then((res) => {
                        console.log(commit);
                        console.log(res.data);
                        commit("setPeriodBudgets", res.data);
                        resolve();

                    })
                    .catch((err) => {
                        console.log(err);
                        reject();
                    });
            })
        },
        createSavingsGoal({ commit }, newSavingsGoal) {
            return new Promise((resolve, reject) => {
                axios.post(API_BASE + 'savings_goals', newSavingsGoal)
                    .then(function (res) {
                        console.log(commit);
                        console.log(res.data);
                        resolve();
                    })
                    .catch(function (err) {
                        console.log(err);
                        reject();
                    });
            })
        },
        commitSavingsGoal({ commit }, newSavingsGoal) {
            return new Promise((resolve, reject) => {
                axios.post(API_BASE + 'savings_goals_commit', newSavingsGoal)
                    .then(function (res) {
                        console.log(commit);
                        console.log(res.data);
                        resolve();
                    })
                    .catch(function (err) {
                        console.log(err);
                        reject();
                    });
            })
        },
        fetchAccounts({ commit }) {
            return new Promise((resolve, reject) => {
            axios
                .get(API_BASE + `accounts/user`)
                .then((res) => {
                    console.log(commit);
                    console.log(res.data);
                    commit("setAccounts", res.data);
                    resolve();
                })
                .catch((err) => {
                    console.log(err);
                    reject();
                });
            })
        },
        createAccount({ commit }, account) {
            return new Promise((resolve, reject) => {
                axios.post(API_BASE + 'accounts', account)
                    .then(function (res) {
                        // console.log(commit);
                        // console.log(res.data);
                        console.log(commit);
                        console.log(res.data);
                        resolve();
                    })
                    .catch(function (err) {
                        console.log(err);
                        reject();
                    });
            })

        },
        updateAccount({ commit }, account) {
            return new Promise((resolve, reject) => {
                axios.put(API_BASE + `accounts/${account.accountId}`, account)
                    .then(function (res) {
                        console.log(commit);
                        console.log(res.data);
                        resolve();
                    })
                    .catch(function (err) {
                        console.log(err);
                        reject();
                    });
            })

        },
        createAccountHistory({ commit }, accountHistory) {
            return new Promise((resolve, reject) => {
                axios.post(API_BASE + 'account_history', accountHistory)
                    .then(function (res) {
                        console.log(commit);
                        console.log(res.data);
                        resolve();
                    })
                    .catch(function (err) {
                        console.log(err);
                        reject();
                    });
            })

        },
        fetchAccountHistories({ commit }) {
            return new Promise((resolve, reject) => {
                axios
                    .get(API_BASE + `account_histories`)
                    .then((res) => {
                        console.log(commit);
                        console.log(res.data);
                        commit("setAccountHistories", res.data);
                        resolve();
                    })
                    .catch((err) => {
                        console.log(err);
                        reject();
                    });
            })
        },
        createStockPurchase({ commit }, stockPurchase) {
            return new Promise((resolve, reject) => {
                axios.post(API_BASE + 'stock_purchase', stockPurchase)
                    .then(function (res) {
                        console.log(commit);
                        console.log(res.data);
                        resolve();
                    })
                    .catch(function (err) {
                        console.log(err);
                        reject();
                    });
            })

        },
        fetchStockPurchases({ commit }) {
            return new Promise((resolve, reject) => {
                axios
                    .get(API_BASE + `stock_purchases`)
                    .then((res) => {
                        console.log(commit);
                        console.log(res.data);
                        commit("setStockPurchases", res.data);
                        resolve();
                    })
                    .catch((err) => {
                        console.log(err);
                        reject();
                    });
            })
        },
        fetchAccountingPeriods({ commit }, year) {
            return new Promise((resolve, reject) => {
                axios
                    .get(API_BASE + `accounting_periods/year/${year}`)
                    .then((res) => {
                        console.log(commit);
                        console.log(res.data);
                        commit("setAccountingPeriods", res.data);
                        let accountingPeriodsData = res.data;

                        let months = [
                            {
                                name: "January",
                                accountingPeriods: []
                            },
                            {
                                name: "February",
                                accountingPeriods: []
                            },
                            {
                                name: "March",
                                accountingPeriods: []
                            },
                            {
                                name: "April",
                                accountingPeriods: []
                            },
                            {
                                name: "May",
                                accountingPeriods: []
                            },
                            {
                                name: "June",
                                accountingPeriods: []
                            },
                            {
                                name: "July",
                                accountingPeriods: []
                            },
                            {
                                name: "August",
                                accountingPeriods: []
                            },
                            {
                                name: "September",
                                accountingPeriods: []
                            },
                            {
                                name: "October",
                                accountingPeriods: []
                            },
                            {
                                name: "November",
                                accountingPeriods: []
                            },
                            {
                                name: "December",
                                accountingPeriods: []
                            },
                        ];

                        console.log(months);


                        for (let i = 0; i < accountingPeriodsData.length; i++) {
                            const splitDate = accountingPeriodsData[i].startDate.split('-');
                            const month = splitDate[1];

                            months[month - 1].accountingPeriods.push(accountingPeriodsData[i]); //add to correct months array index
                            months[month - 1].accountingPeriods.budgetItems = []; //place holder for eventual budgetItems addition
                        }

                        commit("setAccountingPeriodMonths", months);
                        commit("buildBudgetItemsByAccountingMonth", months); //this call must be made after fetchBudgetItems has resolved
                        resolve();

                    })
                    .catch((err) => {
                        console.log(err);
                        reject();
                    });
            })

        },
        updateAccountingPeriod({ commit }, accountingPeriod) {
            return new Promise((resolve, reject) => {
                console.log('updating accounting period');
                console.log(accountingPeriod);
                axios.put(API_BASE + `accounting_periods/${accountingPeriod.accountingPeriodId}`, accountingPeriod)
                    .then(function (res) {
                        console.log(commit);
                        console.log(res.data);
                        resolve();
                    })
                    .catch(function (err) {
                        console.log(err);
                        reject();
                    });
            })
        },
        fetchAccountTypes({ commit }) {
            axios
                .get(API_BASE + `accountTypes`)
                .then((res) => {
                    console.log(commit);
                    console.log(res.data);
                    commit("setAccountTypes", res.data);

                })
                .catch((err) => {
                    console.log(err);
                });
        },
        fetchBudgetTypes({ commit }) {
            axios
                .get(API_BASE + `budget_types`)
                .then((res) => {
                    console.log(commit);
                    console.log(res.data);
                    commit("setBudgetTypes", res.data);

                })
                .catch((err) => {
                    console.log(err);
                });
        },
        periodBudgetsAdjustment({ commit }, adjustment) {
            return new Promise((resolve, reject) => {
                axios.put(API_BASE + `period_budgets/adjustment/${adjustment.amount}/${adjustment.fromMonth}`)
                    .then(function (res) {
                        console.log(commit);
                        console.log(res.data);
                        resolve();
                    })
                    .catch(function (err) {
                        console.log(err);
                        reject();
                    });
            })

        },
        register({ commit }, userData) {
            axios.post(API_BASE + 'users', userData)
                .then(function (res) {
                    let token = res.data.userId;
                    console.log('userId: ' + token);
                    localStorage.setItem('token', token);
                    axios.defaults.headers.common['Authorization'] = token;
                    commit("auth", token);
                })
                .catch(function (err) {
                    console.log(err);
                });
            // let token = (await axios.post("http://localhost:3000/register", registerData)).data;
            // localStorage.setItem("token", token)
            // axios.defaults.headers.common['Authorization'] = token;
            // commit("auth", token);
        },
        login({ commit }, userData) {
            return new Promise((resolve, reject) => {
                axios.post(API_BASE + 'authenticate', userData)
                    .then(function (res) {
                        let token = res.data.token;
                        // console.log('userId: ' + token);
                        localStorage.setItem('token', token);
                        axios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
                        console.log(res.data);
                        commit("setUser", res.data);
                        commit("auth", token);
                        commit("setIsLoggedIn", true); //for displaying success alert
                        resolve(res);
                    })
                    .catch(function (err) {
                        console.log(err);
                        reject();
                    });

            })

            // let token = (await axios.post("http://localhost:3000/register", registerData)).data;
            // localStorage.setItem("token", token)
            // axios.defaults.headers.common['Authorization'] = token;
            // commit("auth", token);
        },

    },
    getters: {
        allusers: state => {
            return state.users;
        },
        getUser: state => state.user,
        getIsLoggedIn: state => state.isLoggedIn,
        getBudgetItems: state => state.budgetItems,
        getBudgetTypes: state => state.budgetTypes,
        getAccountingPeriods: state => state.accountingPeriods,
        getAccounts: state => state.accounts,
        getAccountTypes: state => state.accountTypes,
        getAccountingPeriodMonths: state => state.accountingPeriodMonths,
        getBudgetItemsByMonth: state => state.budgetItemsByMonth,
        getSavingsGoals: state => state.savingsGoals,
        getStockPurchases: state => state.stockPurchases,
        getAccountHistories: state => state.accountHistories,
        getPeriodBudgets: state => state.periodBudgets,
    },

}
);
