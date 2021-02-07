<template>
<div>
  {{getBudgetItems}}
  <v-data-table
      :headers="headers"
      :items="getBudgetItems"
      :items-per-page="5"
      class="elevation-1"
  ></v-data-table>
</div>
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
  },
}
</script>