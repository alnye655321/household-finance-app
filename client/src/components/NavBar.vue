<template>
  <div>
<!--    <v-navigation-drawer-->
<!--        v-model="sidebar"-->
<!--        app-->
<!--        left-->
<!--        offset-y-->
<!--        style="margin-top: 50px;"-->
<!--        class="hidden-md-and-up"-->
<!--    >-->
<!--      <v-list-item-->
<!--          v-for="item in navItems"-->
<!--          :key="item.text"-->
<!--          :to="item.route"-->
<!--          active-class="blue&#45;&#45;text"-->
<!--      >-->
<!--        <v-list-item-icon class="mr-2">-->
<!--          <v-icon small>{{ item.icon }}</v-icon>-->
<!--        </v-list-item-icon>-->
<!--        <v-list-item-title>{{ item.text }}</v-list-item-title>-->
<!--      </v-list-item>-->
<!--    </v-navigation-drawer>-->

    <v-app-bar dense dark absolute color="primary">
      <span class="hidden-md-and-up">
        <v-app-bar-nav-icon @click="sidebar = !sidebar"></v-app-bar-nav-icon>
      </span>
      <v-spacer class="hidden-md-and-up"></v-spacer>
      <v-toolbar-title class="text-uppercase">
        <router-link to="/dashboard" tag="span" style="cursor: pointer" class="text-h5">
          <!-- <span class="grey--text text--lighten-1">Vue</span> -->
          <span class="font-weight-bold">{{ systemName }}</span>
        </router-link>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <!-- menu -->
      <v-menu offset-y>
        <template v-slot:activator="{ on }" class="ml-4">
          <div v-on="on" class="d-flex align-center">
            <v-avatar size="30px" color="blue lighten-2" class="ml-2" style="cursor: pointer">
              <span class="white--text">{{nameInitials}}</span>
            </v-avatar>
            <div class="hidden-sm-and-down">
              <v-btn text class="pa-1">
                <span class="text-capitalize">{{typeof getUser.name != 'undefined' ? getUser.name : 'Not Logged In'}}</span>
                <v-icon>mdi-plus</v-icon>
              </v-btn>
            </div>
          </div>
        </template>
        <v-list>
          <v-list-item v-for="item in menuItems" :key="item.text" router dense :to="item.route">
            <v-list-item-icon class="mr-2">
              <v-icon small>{{ item.icon }}</v-icon>
            </v-list-item-icon>
            <v-list-item-title>{{ item.text }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>
    <v-app-bar
        dense
        class="hidden-sm-and-down secondary-toolbar mt-12"
        :elevate-on-scroll="true"

    >
      <v-toolbar-items>
        <v-btn text v-for="item in navItems" :key="item.text" :to="item.route" :ripple="false">
          <span>
            <v-icon small class="blue--text mr-1">{{ item.icon }}</v-icon>
          </span>
          <span class="mt-1 text-subtitle-2 text-capitalize">{{ item.text }}</span>
        </v-btn>
      </v-toolbar-items>
    </v-app-bar>
  </div>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  components: {
  },
  computed: {
    ...mapGetters([
      'getUser',
    ]),
  },
  data() {
    return {
      systemName: "Finance Tracker",
      sidebar: false,
      navItems: [
        { icon: "mdi-cash-check", text: "Budget Items", route: "/budgetitems" },
        { icon: "mdi-fire", text: "Assets", route: "/assets" },
        { icon: "mdi-credit-card-plus", text: "Accounts", route: "/accounts" },
        { icon: "mdi-fire", text: "Savings Goals", route: "/savings" },
        { icon: "mdi-fire", text: "Settings", route: "/settings" },
      ],
      menuItems: [
        { icon: "mdi-fire", text: "Login", route: "/login" },
        { icon: "mdi-fire", text: "Stock Purchase", route: "/stockpurchase" },
        { icon: "mdi-fire", text: "Account History", route: "/accounthistory" },
      ]
    };
  }
};
</script>

<style scoped lang="scss">
::v-deep .v-toolbar__content {
  max-width: 1185px;
  margin: auto;
}
::v-deep .v-toolbar__items > a.v-btn--active {
  .v-btn__content span {
    font-weight: bold;
  }
}
::v-deep .v-toolbar__items > a.v-btn--active:after {
  content: "";
  position: absolute;
  left: 18px;
  bottom: 0;
  height: 2px;
  width: 75%;
  border-bottom: 2px solid #1e88e5;
}
::v-deep .secondary-toolbar .v-toolbar__content {
  padding: 0;
}
::v-deep .v-btn:before {
  background-color: transparent !important;
}
</style>