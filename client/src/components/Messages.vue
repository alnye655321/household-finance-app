<template>
  <div>
    <h3>{{ getUsers }}</h3>
    <h4>Messages</h4>
    <v-card max-width="450" class="mx-auto">
      <v-toolbar color="cyan" dark>
        <v-app-bar-nav-icon></v-app-bar-nav-icon>

        <v-toolbar-title>Inbox</v-toolbar-title>

        <v-spacer></v-spacer>

        <v-btn icon>
          <v-icon>mdi-magnify</v-icon>
        </v-btn>
      </v-toolbar>

      <v-list three-line>
        <template v-for="(item, index) in items">
          <v-subheader
            v-if="item.header"
            :key="item.header"
            v-text="item.header"
          ></v-subheader>

          <v-divider
            v-else-if="item.divider"
            :key="index"
            :inset="item.inset"
          ></v-divider>

          <v-list-item v-else :key="item.title">
            <v-list-item-avatar>
              <v-img :src="item.avatar"></v-img>
            </v-list-item-avatar>

            <v-list-item-content>
              <v-list-item-title v-html="item.title"></v-list-item-title>
              <v-list-item-subtitle
                v-html="item.subtitle"
              ></v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </template>
      </v-list>
    </v-card>
  </div>
</template>
<script>
import axios from "axios";

export default {
  data() {
    return {
      items: [], //local var here, being transformed by store value of users below
    };
  },
  created() {
    //this.$store.dispatch('setUsers'); //trigger an async action in store
    // this.fetchUsers();
    // this.testDelay();
  },
  methods: {
    fetchUsers: function () {
      axios
        .get("http://localhost:8090/api/v1/users")
        .then((res) => {
          this.$store.commit("assignUsers", res.data);

          //test display in card-----------------------------------
          let data = res.data;
          let itemTemps = [];

          // console.log(this.$store);

          for (let i = 0; i < this.$store.getters.allusers.length; i++) {
            itemTemps[i] = {
              avatar: "https://cdn.vuetifyjs.com/images/lists/5.jpg",
              title: data[i].name,
              subtitle: data[i].email,
            };
          }
          this.items = itemTemps;
          console.log(this.$store.getters.allusers);
          // console.log(this);
          //test display in card-----------------------------------
        })
        .catch((err) => {
          console.log(err);
        });
    },
    testDelay: function () {
      let _this = this;
      setTimeout(function () {
        console.log("delay");
        let user = {
          userId: 3,
          name: "betty2",
          hashedPassword: "pw2",
          email: "a2@aol.com",
        };
        console.log(this);
        _this.$store.commit("newUser", user);
      }, 5000);
    },
  },
  computed: {
    //can be used within template code
    getUsers() {
      return this.$store.state.users;
    },
  },
};

// [
//   { header: "Today" },
//   {
//     avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
//     title: "Brunch this weekend?",
//     subtitle: `<span class="text--primary">Ali Connors</span> &mdash; I'll be in your neighborhood doing errands this weekend. Do you want to hang out?`,
//   },
//   { divider: true, inset: true },
//   {
//     avatar: "https://cdn.vuetifyjs.com/images/lists/2.jpg",
//     title: 'Summer BBQ <span class="grey--text text--lighten-1">4</span>',
//     subtitle: `<span class="text--primary">to Alex, Scott, Jennifer</span> &mdash; Wish I could come, but I'm out of town this weekend.`,
//   },
//   { divider: true, inset: true },
//   {
//     avatar: "https://cdn.vuetifyjs.com/images/lists/3.jpg",
//     title: "Oui oui",
//     subtitle:
//       '<span class="text--primary">Sandra Adams</span> &mdash; Do you have Paris recommendations? Have you ever been?',
//   },
//   { divider: true, inset: true },
//   {
//     avatar: "https://cdn.vuetifyjs.com/images/lists/4.jpg",
//     title: "Birthday gift",
//     subtitle:
//       '<span class="text--primary">Trevor Hansen</span> &mdash; Have any ideas about what we should get Heidi for her birthday?',
//   },
//   { divider: true, inset: true },
//   {
//     avatar: "https://cdn.vuetifyjs.com/images/lists/5.jpg",
//     title: "Recipe to try",
//     subtitle:
//       '<span class="text--primary">Britta Holt</span> &mdash; We should eat this: Grate, Squash, Corn, and tomatillo Tacos.',
//   },
// ],
</script>


