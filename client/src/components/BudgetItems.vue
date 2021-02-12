<template>
<div>
  <v-tabs v-model="tab" background-color="transparent" color="basil" grow>
    <v-tab v-for="item in getBudgetItemsByMonth" :key="item.name"  @click="test(tab)">
      {{ item.name }}
    </v-tab>
  </v-tabs>

  <v-tabs-items v-model="tab">
    <v-tab-item v-for="item in getBudgetItemsByMonth" :key="item.name">

<!--      <v-card max-width="375" class="mx-auto" v-for="account in getAccounts" :key="account.accountId">-->
      <v-card color="basil" flat>
        <v-card-text>{{ item.accountingPeriods[1].endDate  }}</v-card-text>
      </v-card>


      <v-card>
        <v-data-table v-if="item.accountingPeriods[0].budgetItems" :headers="headers" :items="item.accountingPeriods[0].budgetItems" :items-per-page="5" class="elevation-1">

          <template v-slot:item.actions="{ item }">
            <v-icon small class="mr-2" @click="editItem(item)">mdi-pencil</v-icon>
            <v-icon small @click="deleteItem(item)">mdi-delete</v-icon>
          </template>

        </v-data-table>
      </v-card>

<!--      TODO need a more significant divider here, perhaps two tables side by side-->
        <v-spacer></v-spacer>

      <v-card>
        <v-data-table v-if="item.accountingPeriods[1].budgetItems" :headers="headers" :items="item.accountingPeriods[1].budgetItems" :items-per-page="5" class="elevation-1">

          <template v-slot:item.actions="{ item }">
            <v-icon small class="mr-2" @click="editItem(item)">mdi-pencil</v-icon>
            <v-icon small @click="deleteItem(item)">mdi-delete</v-icon>
          </template>

        </v-data-table>
      </v-card>


    </v-tab-item>
  </v-tabs-items>


  <v-btn class="mt-12" color="primary" @click="showOverlay = !showOverlay">New Budget Item</v-btn>

<!--  Begin Overlay-->
  <v-overlay :absolute="overlayAbsolute" :opacity="overlayOpacity" :value="showOverlay" :z-index="overlayzIndex">
    <v-form ref="form" v-if="selectedItem.hasOwnProperty('name')" lazy-validation>
      <v-text-field v-model="selectedItem.name" label="Name" required> </v-text-field>

      <v-select v-model="selectedItem.budgetType"
          :hint="`${selectedItem.type}, ${selectedItem.budgetTypeId}`"
          :items="getBudgetTypes"
          item-text="type"
          item-value="budgetTypeId"
          label="Select"
          persistent-hint
          return-object
          single-line
      ></v-select>

      <v-select v-model="selectedItem.accountingPeriod"
                :items="getAccountingPeriods"
                item-text="startDate"
                item-value="accountingPeriodId"
                label="Select"
                return-object
                single-line
      ></v-select>

      <v-text-field v-model="selectedItem.budgetType.type" label="BudgetType" required></v-text-field>
      <v-text-field v-model="selectedItem.account.name" label="Account" required></v-text-field>
      <v-text-field label="Amount" v-model="selectedItem.amount" prefix="$"></v-text-field>
      <v-text-field disabled label="Created Date" v-model="selectedItem.createdDate"></v-text-field>
<!--      <v-date-picker v-model="selectedItem.createdDate"></v-date-picker>-->
      <v-text-field v-model="selectedItem.committed" label="Committed" required></v-text-field>

<!--      <v-text-field v-model="email" label="E-mail" required></v-text-field>-->

<!--      <v-select v-model="select" :items="items" label="Item" required></v-select>-->

<!--      <v-btn :disabled="!valid" color="success" class="mr-4" @click="test">Validate</v-btn>-->

      <v-btn color="primary" class="mr-4" @click="updateItem">Submit</v-btn>

      <v-btn color="error" class="mr-4" @click="test">Reset Form</v-btn>

      <v-btn color="warning" @click="test">Reset Validation</v-btn>

    </v-form>
    <v-btn color="primary" @click="showOverlay = false">Hide Overlay</v-btn>
  </v-overlay>
  <!--  End Overlay-->

</div>




<!--  <v-icon>fas fa-lock</v-icon>-->
<!--  <v-spacer></v-spacer>-->
<!--  <v-icon>mdi-folder</v-icon>-->
</template>

<script>

import { mapGetters } from 'vuex'

export default {
  data: () => ({
    tab: null, //corresponding to index. update to set active tab --> 0 == January... 11 == December
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
      'getBudgetTypes',
      'getAccountingPeriods',
      'getUser',
      'getAccountingPeriodMonths',
      'getBudgetItemsByMonth',
    ]),
    // formTitle() {
    //   return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
    // },
  },

  watch: {
  },

  created() {
    this.$store.dispatch("fetchBudgetTypes");

    this.$store.dispatch("fetchAccounts", this.$store.getters.getUser.userId);

    this.$store.dispatch("fetchBudgetItems", this.$store.getters.getUser.userId) //important that budget items are sent first
        .then(() => {
          this.$store.dispatch("fetchAccountingPeriods"); //will eventually commit a mutation that arranges budget items into a months array - getBudgetItemsByMonth
        });

    const today = new Date();
    this.tab = today.getMonth(); //set the active tab to the current month

  },

  methods: {
    addItem() {
      this.showOverlay = true;
    },
    editItem(item) {
      this.selectedItem = item;
      this.showOverlay = true;
      console.log(item);
    },
    updateItem() {
      //TODO should load data again here from server after complete, make into an async
      this.$store.dispatch("updateBudgetItem", this.selectedItem);
      // console.log(item);
    },
    deleteItem(item) {
      console.log(item);
    },
    test(tab) {
      console.log(tab);
    },
  },
}
</script>