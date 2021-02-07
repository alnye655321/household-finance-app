import Vue from 'vue';
import Vuex from 'vuex';
import axios from "axios";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        users: [],
        token: localStorage.getItem('token') || '',
        isLoggedIn : false,
        budgetItems: [],
    },
    mutations: {
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

    },
    actions: { //can be used to perform async requests
        setUsers({ commit }) {
            axios
                .get("http://localhost:8080/api/v1/users")
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
                .get(`http://localhost:8080/api/v1/users/${id}`)
                .then((res) => {
                    console.log(commit);
                    console.log(res.data);

                })
                .catch((err) => {
                    console.log(err);
                });
        },
        fetchBudgetItems({ commit }, id) {
            axios
                .get(`http://localhost:8080/api/v1/budget_items/user/${id}`)
                .then((res) => {
                    console.log(commit);
                    console.log(res.data);
                    commit("setBudgetItems", res.data);

                })
                .catch((err) => {
                    console.log(err);
                });
        },
        register({ commit }, userData) {
            axios.post('http://localhost:8080/api/v1/users', userData)
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
            axios.post('http://localhost:8080/api/v1/authenticate', userData)
                .then(function (res) {
                    let token = res.data.token;
                    console.log('userId: ' + token);
                    localStorage.setItem('token', token);
                    axios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
                    commit("auth", token);
                    commit("setIsLoggedIn", true); //for displaying success alert
                })
                .catch(function (err) {
                    console.log(err);
                });
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
        getIsLoggedIn: state => state.isLoggedIn,
        getBudgetItems: state => state.budgetItems,
    },

}
);
