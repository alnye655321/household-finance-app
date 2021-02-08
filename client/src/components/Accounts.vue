<template>
  <div>
  <v-card max-width="375" class="mx-auto" v-for="account in getAccounts" :key="account.accountId">
    <v-img
        src="https://cdn.vuetifyjs.com/images/lists/ali.png"
        height="300px"
        dark
    >
      <v-row class="fill-height">
        <v-card-title>
          <v-btn dark icon>
            <v-icon>mdi-chevron-left</v-icon>
          </v-btn>

          <v-spacer></v-spacer>

          <v-btn dark icon class="mr-4">
            <v-icon>mdi-pencil</v-icon>
          </v-btn>

          <v-btn dark icon>
            <v-icon>mdi-dots-vertical</v-icon>
          </v-btn>
        </v-card-title>

        <v-spacer></v-spacer>

        <v-card-title class="white--text pl-12 pt-12">
          <div class="display-1 pl-12 pt-12">
            {{account.name}}
          </div>
        </v-card-title>
      </v-row>
    </v-img>

    <v-list two-line>
      <v-list-item>
        <v-list-item-icon>
          <v-icon color="indigo">mdi-currency-usd</v-icon>
        </v-list-item-icon>

        <v-list-item-content>
          <v-list-item-title>{{account.balance}}</v-list-item-title>
          <v-list-item-subtitle>Balance</v-list-item-subtitle>
        </v-list-item-content>
<!--        <v-list-item-icon>-->
<!--          <v-icon>mdi-message-text</v-icon>-->
<!--        </v-list-item-icon>-->
      </v-list-item>

      <v-divider inset></v-divider>

      <v-list-item>
        <v-list-item-icon>
          <v-icon color="indigo">mdi-calendar-range</v-icon>
        </v-list-item-icon>

        <v-list-item-content>
          <v-list-item-title>{{account.dateOpened}}</v-list-item-title>
          <v-list-item-subtitle>Date Opened</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-divider inset></v-divider>

      <v-list-item>
        <v-list-item-icon>
          <v-icon color="indigo">mdi-cash-multiple</v-icon>
        </v-list-item-icon>

        <v-list-item-content>
          <v-list-item-title>{{account.interestRate}}</v-list-item-title>
          <v-list-item-subtitle>Interest Rate</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-divider inset></v-divider>

      <v-list-item>
        <v-list-item-icon>
          <v-icon color="indigo">mdi-cash-multiple</v-icon>
        </v-list-item-icon>

        <v-list-item-content>
          <v-list-item-title>{{account.accountType.accountType}}</v-list-item-title>
          <v-list-item-subtitle>Account Type</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>


      <v-divider inset v-if="account.dateClosed"></v-divider>

      <v-list-item v-if="account.dateClosed">
        <v-list-item-icon>
          <v-icon color="indigo">mdi-cash-multiple</v-icon>
        </v-list-item-icon>

        <v-list-item-content>
          <v-list-item-title>{{account.dateClosed}}</v-list-item-title>
          <v-list-item-subtitle>Date Closed</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>




    </v-list>
  </v-card>
  </div>
</template>

<script>

import { mapGetters } from 'vuex'

export default {
  data: () => ({
  }),
  computed: {
    ...mapGetters([
      'getAccounts',
      'getUser',
    ]),
    // formTitle() {
    //   return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
    // },
  },
  created() {
    this.$store.dispatch("fetchAccounts", this.$store.getters.getUser.userId);
  },




}

</script>