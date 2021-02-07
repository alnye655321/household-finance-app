<template>
<div>
  <v-data-table :headers="headers" :items="getBudgetItems" :items-per-page="5" class="elevation-1">

    <template v-slot:item.actions="{ item }">
      <v-icon small class="mr-2" @click="editItem(item)">mdi-pencil</v-icon>
      <v-icon small @click="deleteItem(item)">mdi-delete</v-icon>
    </template>

  </v-data-table>

</div>




<!--  <v-icon>fas fa-lock</v-icon>-->
<!--  <v-spacer></v-spacer>-->
<!--  <v-icon>mdi-folder</v-icon>-->
</template>

<script>

import { mapGetters } from 'vuex'

export default {
  data: () => ({
    headers: [
      {
        text: 'Name',
        align: 'start',
        sortable: false,
        value: 'name',
      },
      {text: 'Name', value: 'name'},
      {text: 'Type', value: 'budgetType.type'},
      {text: 'Account', value: 'account.name'},
      {text: 'Amount', value: 'amount'},
      {text: 'Created', value: 'createdDate'},
      {text: 'Committed', value: 'committed'},
      {text: 'Actions', value: 'actions'}, //adding extra actions column, not in server returned object, for space to display action buttons
    ],
  }),

  computed: {
    ...mapGetters([
      'getBudgetItems',
    ]),
    // formTitle() {
    //   return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
    // },
  },

  watch: {
  },

  created() {
    this.$store.dispatch("fetchBudgetItems", 1); //TODO needs to be replaced with actual userId, add it to store
  },

  methods: {
    editItem(item) {
      console.log(item);
    },
    deleteItem(item) {
      console.log(item);
    },
  },
}
</script>