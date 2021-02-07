<template>
<div>
  <v-data-table :headers="headers" :items="getBudgetItems" :items-per-page="5" class="elevation-1">

    <template v-slot:item.actions="{ item }">
      <v-icon small class="mr-2" @click="editItem(item)">mdi-pencil</v-icon>
      <v-icon small @click="deleteItem(item)">mdi-delete</v-icon>
    </template>

  </v-data-table>

  <v-btn
      class="mt-12"
      color="primary"
      @click="showOverlay = !showOverlay"
  >
    Show Overlay
  </v-btn>
  <v-overlay :absolute="overlayAbsolute" :opacity="overlayOpacity" :value="showOverlay" :z-index="overlayzIndex">
    <v-form ref="form" v-if="selectedItem.hasOwnProperty('name')" lazy-validation>
      <v-text-field v-model="selectedItem.name" label="Name" required> </v-text-field>
      <v-text-field v-model="selectedItem.budgetType.type" label="Name" required></v-text-field>
      <v-text-field v-model="selectedItem.account.name" label="Name" required></v-text-field>

<!--      <v-text-field v-model="email" label="E-mail" required></v-text-field>-->

<!--      <v-select v-model="select" :items="items" label="Item" required></v-select>-->

<!--      <v-btn :disabled="!valid" color="success" class="mr-4" @click="test">Validate</v-btn>-->

      <v-btn color="error" class="mr-4" @click="test">Reset Form</v-btn>

      <v-btn color="warning" @click="test">Reset Validation</v-btn>

    </v-form>
    <v-btn color="primary" @click="showOverlay = false">Hide Overlay</v-btn>
  </v-overlay>

</div>




<!--  <v-icon>fas fa-lock</v-icon>-->
<!--  <v-spacer></v-spacer>-->
<!--  <v-icon>mdi-folder</v-icon>-->
</template>

<script>

import { mapGetters } from 'vuex'

export default {
  data: () => ({
    selectedItem: { }, //updated for new/edit item form
    overlayAbsolute: false,
    overlayOpacity: 0.46,
    showOverlay: false,
    overlayzIndex: 5,
    headers: [
      {text: 'Name',  align: 'start',  sortable: true, value: 'name', },
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
      this.selectedItem = item;
      this.showOverlay = true;
      console.log(item);
    },
    deleteItem(item) {
      console.log(item);
    },
    test() {
      console.log('');
    },
  },
}
</script>