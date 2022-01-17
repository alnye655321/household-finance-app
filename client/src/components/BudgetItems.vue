<template>
<div>
  <v-tabs v-model="tab" background-color="transparent" color="basil" grow>
    <v-tab v-for="item in getBudgetItemsByMonth" :key="item.name"  @click="test(item)">
      {{ item.name }}
    </v-tab>
  </v-tabs>

  <v-tabs-items v-model="tab">
    <v-tab-item v-for="item in getBudgetItemsByMonth" :key="item.name">

<!--      <v-card max-width="375" class="mx-auto" v-for="account in getAccounts" :key="account.accountId">-->
<!--      <v-card color="basil" flat>-->
<!--        <v-card-text>{{ item.accountingPeriods[1].endDate  }}</v-card-text>-->
<!--      </v-card>-->
      <v-spacer></v-spacer>

      <v-row>
        <v-col cols="10">
      <AccountBar></AccountBar>
        </v-col>

        <v-col cols="2">
          <v-row>
            <v-select
                v-model="year"
                :items="years"
                menu-props="auto"
                label="Select"
                hide-details
                prepend-icon="mdi-calendar"
                @change="yearChange"
                single-line
            ></v-select>
          </v-row>
          <v-row>
            <v-select
                v-model="userDisplay"
                :items="allusers"
                item-text="name"
                item-value="userId"
                menu-props="auto"
                label="Select"
                @change="userDisplaySwitch"
                hide-details
                prepend-icon="mdi-account"
                single-line
            ></v-select>

          </v-row>

        </v-col>

      </v-row>

      <v-spacer></v-spacer>

      <v-row>
<!--        accounting bi-weekly period 1-->
        <v-col>
      <v-card>
        <v-app-bar dark color="pink">
          <v-app-bar-nav-icon></v-app-bar-nav-icon>

          <v-toolbar-title>{{item.accountingPeriods[0].startDate}} - {{item.accountingPeriods[0].endDate}}</v-toolbar-title>

          <v-spacer></v-spacer>

          <v-btn icon v-if="false">
            <v-icon>mdi-magnify</v-icon>
          </v-btn>

          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-btn icon @click="updateFormForItemCreation(0); showOverlay = !showOverlay;">
                <v-icon v-bind="attrs" v-on="on">mdi-plus-circle</v-icon>
              </v-btn>
            </template>
            <span>Add Item</span>
          </v-tooltip>

        </v-app-bar>

        <v-data-table v-if="item.accountingPeriods[0].budgetItems" :headers="headers" :items="item.accountingPeriods[0].budgetItems" :items-per-page="5" class="elevation-1">

          <template v-slot:item.committed="{ item }">
            <v-switch v-model="item.committed" @click="selectedItem = item; updateItem();"></v-switch>
          </template>

          <template v-slot:item.actions="{ item }">
            <v-icon small class="mr-2" v-if="!isItemAccountTransfer(item)" @click="createFormActive = false; editItem(item);" :disabled="isItemCommitted(item)">mdi-pencil</v-icon>
            <v-icon small @click="deleteItemConfirm(item)" :disabled="isItemCommitted(item)">mdi-delete</v-icon>
          </template>

        </v-data-table>
      </v-card>
        </v-col>
<!--        accounting bi-weekly period 2-->
        <v-col>
      <v-card>
        <v-app-bar dark color="purple">
          <v-app-bar-nav-icon></v-app-bar-nav-icon>

          <v-toolbar-title>{{item.accountingPeriods[1].startDate}} - {{item.accountingPeriods[1].endDate}}</v-toolbar-title>

          <v-spacer></v-spacer>

          <v-btn icon v-if="false">
            <v-icon>mdi-magnify</v-icon>
          </v-btn>

          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-btn icon @click="updateFormForItemCreation(1); showOverlay = !showOverlay;">
                <v-icon v-bind="attrs" v-on="on">mdi-plus-circle</v-icon>
              </v-btn>
            </template>
            <span>Add Item</span>
          </v-tooltip>

        </v-app-bar>
        <v-data-table v-if="item.accountingPeriods[1].budgetItems" :headers="headers" :items="item.accountingPeriods[1].budgetItems" :items-per-page="5" class="elevation-1">

          <template v-slot:item.committed="{ item }">
            <v-switch v-model="item.committed" @click="selectedItem = item; updateItem();"></v-switch>
          </template>

          <template v-slot:item.actions="{ item }">
            <v-icon small class="mr-2" v-if="!isItemAccountTransfer(item)" @click="createFormActive = false; editItem(item);" :disabled="isItemCommitted(item)">mdi-pencil</v-icon>
            <v-icon small @click="deleteItemConfirm(item);" :disabled="isItemCommitted(item)">mdi-delete</v-icon>
          </template>

        </v-data-table>
      </v-card>
        </v-col>
      </v-row>

      <v-row justify="center">
        <v-col>
          <AccountTransfer v-if="item" :parentAccountingPeriod="item.accountingPeriods[0]"></AccountTransfer>
        </v-col>
        <v-col>
          <AccountTransfer v-if="item" :parentAccountingPeriod="item.accountingPeriods[1]"></AccountTransfer>
        </v-col>
      </v-row>

<!--      Notes Section-->
      <v-row>
        <v-col>
          <v-textarea
              name="input-7-1"
              filled
              label="Notes"
              v-model="item.accountingPeriods[0].notes"
              @blur="updateAccountingPeriodNotes(item.accountingPeriods[0])"
              auto-grow
          ></v-textarea>
        </v-col>

        <v-col>
          <v-textarea
              name="input-7-2"
              filled
              label="Notes"
              v-model="item.accountingPeriods[1].notes"
              @blur="updateAccountingPeriodNotes(item.accountingPeriods[1])"
              auto-grow
          ></v-textarea>
        </v-col>
      </v-row>


    </v-tab-item>
  </v-tabs-items>

<!--  Begin Create/Edit Budget Item Overlay-->
  <v-overlay :absolute="overlayAbsolute" :opacity="overlayOpacity" :value="showOverlay" :z-index="overlayZIndex">
    <v-form ref="form" v-if="selectedItem.hasOwnProperty('name')" lazy-validation>

      <!--  start alert-->
      <v-alert
          v-model="alert"
          close-text="Close Alert"
          color="deep-purple accent-4"
          dark
          dismissible
      >
        Please complete all form items
      </v-alert>
      <!--  end alert-->

      <v-text-field v-model="selectedItem.name" :rules="nameRules" label="Name" required> </v-text-field>

      <v-select v-model="selectedItem.budgetType"
                hint="Type"
                :items="filteredBudgetTypes()"
                item-text="type"
                item-value="budgetTypeId"
                label="Select Type"
                :rules="budgetTypeRules"
                persistent-hint
                return-object
                single-line
                required
      ></v-select>

      <v-select v-model="selectedItem.accountingPeriod"
                hint="Accounting Period"
                :items="getAccountingPeriods"
                item-text="startDate"
                item-value="accountingPeriodId"
                label="Select Accounting Period"
                :rules="accountingPeriodRules"
                disabled
                persistent-hint
                return-object
                single-line
                required
      ></v-select>

      <v-select v-model="selectedItem.account"
                hint="Account"
                :items="filteredAccounts()"
                item-text="name"
                item-value="accountId"
                label="Select Account"
                :rules="accountRules"
                @change="accountListChange()"
                persistent-hint
                return-object
                single-line
                required
      ></v-select>

<!--      <v-text-field v-model="selectedItem.account.name" label="Account" required></v-text-field>-->

      <v-text-field label="Amount" v-model="selectedItem.amount" :rules="amountRules" prefix="$" required></v-text-field>


      <v-btn tile color="primary" @click="showSavingsGoal = !showSavingsGoal"  v-if="selectedItem.budgetType.type === 'Savings Contribution' && !showSavingsGoal">
        <v-icon left>mdi-plus</v-icon>
        Add Savings Goal
      </v-btn>

      <v-btn tile color="error" @click="showSavingsGoal = !showSavingsGoal; selectedItem.savingsGoal = null;" v-if="showSavingsGoal">
        <v-icon left>mdi-minus</v-icon>
        Remove Savings Goal
      </v-btn>

      <!--      v-if="selectedItem.account.hasOwnProperty('accountType') && selectedItem.account.accountType.accountType === 'Savings' && selectedItem.budgetType.type !== 'Savings Contribution' "-->
      <v-select v-model="selectedItem.savingsGoal"
                v-if="showSavingsGoal"
                hint="Savings Goal"
                :items="getSavingsGoals"
                item-text="name"
                item-value="savingsGoalId"
                label="Select Savings Goal"
                persistent-hint
                return-object
                single-line
      ></v-select>

      <v-text-field disabled label="Created Date" v-model="selectedItem.createdDate"></v-text-field>
<!--      <v-date-picker v-model="selectedItem.createdDate"></v-date-picker>-->


<!--      <v-text-field v-model="selectedItem.committed" label="Committed" required></v-text-field>-->

<!--      <v-text-field v-model="email" label="E-mail" required></v-text-field>-->

<!--      <v-select v-model="select" :items="items" label="Item" required></v-select>-->

<!--      <v-btn :disabled="!valid" color="success" class="mr-4" @click="test">Validate</v-btn>-->




      <v-btn color="success" class="mr-4" v-if="createFormActive" :disabled="!valid" @click="createItem">Submit</v-btn>
      <v-btn color="success" class="mr-4" v-if="!createFormActive" :disabled="!valid" @click="updateItem">Submit</v-btn>
<!--      <v-btn color="error" class="mr-4" @click="test">Reset Form</v-btn>-->
<!--      <v-btn color="warning" @click="test">Reset Validation</v-btn>-->
      <v-btn color="error" @click="showOverlay = false">Cancel</v-btn>

    </v-form>

  </v-overlay>
  <!--  End Create/Edit Budget Item Overlay-->

<!--begin delete budget item overlay-->
  <v-overlay :z-index="overlayZIndex" :value="deleteConfirmOverlay">

    <!--  start delete alert-->
    <v-alert
        v-model="deleteAlert"
        close-text="Close Alert"
        color="deep-purple accent-4"
        dark
    >
      Please uncommit budget item before deleting it.
    </v-alert>
    <!--  end alert-->

    <v-btn v-if="!deleteAlert" class="white--text mr-4" color="teal" @click="deleteItem(); deleteConfirmOverlay = false;">Confirm Delete</v-btn>
    <v-btn class="white--text mr-4" color="red" @click="deleteAlert = false; deleteConfirmOverlay = false;">Cancel</v-btn>
  </v-overlay>
<!--  end delete budget item overlay-->

</div>

<!--  <v-icon>fas fa-lock</v-icon>-->
<!--  <v-spacer></v-spacer>-->
<!--  <v-icon>mdi-folder</v-icon>-->
</template>

<script>

import { mapGetters } from 'vuex'
import AccountBar from "@/components/AccountBar";
import AccountTransfer from "@/components/AccountTransfer";

export default {
  data: () => ({
    year: new Date().getFullYear(),
    years: [
        2021,
        2022,
        2023
    ],
    userDisplay: -1,
    userDisplays: [],
    showSavingsGoal: false,
    deleteAlert: false,
    alert: false,
    valid: true,
    selectedAccountingPeriods: {},
    nameRules: [
      v => !!v || 'Name is required',
      v => (v && v.length <= 20) || 'Name must be less than 20 characters',
    ],
    budgetTypeRules:[
      v => !!v || 'Budget Type is required', //Select Type
    ],
    accountingPeriodRules:[
      v => !!v || 'Accounting Period is required', //Select Type
    ],
    accountRules:[
      v => !!v || 'Account  is required', //v && v != 'Select Account'
    ],
    amountRules:[
      v => !!v || 'Amount  is required', //Select Type
      v => (v && isFinite(v)) || 'Please Enter A Number For Amount',
    ],
    deleteConfirmOverlay: false,
    createFormActive: false, //if the overlay form is being used to create a new budget item, default is false for editing. Will affect the method called on submit
    tab: null, //corresponding to index. update to set active tab --> 0 == January... 11 == December
    selectedItem: { }, //updated for new/edit item form
    overlayAbsolute: false,
    overlayOpacity: 0.86,
    showOverlay: false,
    overlayZIndex: 5,
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
  components: {
    AccountBar,
    AccountTransfer,
  },
  computed: {
    ...mapGetters([
      'getBudgetItems',
      'getBudgetTypes',
      'getAccountingPeriods',
      'getUser',
      'allusers',
      'getAccountingPeriodMonths',
      'getBudgetItemsByMonth',
      'getAccounts',
      'getSavingsGoals',
    ]),
    // formTitle() {
    //   return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
    // },
    commitedLabel() {
      if (this.selectedItem.commited) {
        return 'Commited To Account';
      }
      else {
        return 'Not Commited To Account';
      }
    },
  },


  watch: {
  },

  created() {
    //if not authenticated try authenticating from existing token
    const currentUser = this.$store.getters.getUser;

    if (typeof currentUser.name === 'undefined' && typeof localStorage.token !== 'undefined') {
      console.log('Using Existing Token For Login');
      this.$store.dispatch("setUserFromExistingToken", localStorage.token).then(() => {
        this.loadInitData();
      });
    }
    else {
      this.loadInitData();

      const today = new Date();
      this.tab = today.getMonth(); //set the active tab to the current month
    }

  },

  methods: {
    loadInitData() {
      this.$store.dispatch("fetchBudgetTypes");

      this.$store.dispatch("fetchAccounts");

      if (this.userDisplay === -1) {
        this.userDisplay = this.$store.getters.getUser.userId;
      }

      const payload = {
        id: this.userDisplay,
        year: this.year
      };

      this.$store.dispatch("fetchBudgetItems", payload) //important that budget items are sent first
          .then(() => {
            this.$store.dispatch("fetchAccountingPeriods", this.year) //will eventually commit a mutation that arranges budget items into a months array - getBudgetItemsByMonth
          });

      this.$store.dispatch("fetchSavingsGoals");
    },
    filteredAccounts() {
      if (typeof this.selectedItem.budgetType.type !== 'undefined' && this.selectedItem.budgetType.type === 'Savings Contribution') {
        const accounts = this.$store.getters.getAccounts;
        const savingsAccounts = accounts.filter(e => e.accountType.accountType === 'Savings');
        return savingsAccounts;
      }
      else {
        return this.$store.getters.getAccounts;
      }
    },
    filteredBudgetTypes() {
      const budgetTypes = this.$store.getters.getBudgetTypes;
      return budgetTypes.filter(e => e.type !== 'Account Transfer'); //hide account transfers from normal budget item create - they have their own UI interface
    },
    yearChange() {
      console.log('year changing');
      console.log(this.year);
      this.loadInitData();
    },
    accountListChange() {
      if (typeof this.selectedItem.account.accountType !== 'undefined' && this.selectedItem.account.accountType !== 'Savings') {
        this.showSavingsGoal = false;
        this.selectedItem.savingsGoal = null;
      }
    },
    addItem() {
      this.showOverlay = true;
    },
    editItem(item) {
      this.selectedItem = item;
      this.showOverlay = true;
    },
    createItem() {
      const valid = this.$refs.form.validate();

      if (valid
          && typeof this.selectedItem.account.accountId !== 'undefined' && this.selectedItem.account.accountId > -1
          &&  typeof this.selectedItem.budgetType.budgetTypeId !== 'undefined' && this.selectedItem.budgetType.budgetTypeId > -1
          &&  typeof this.selectedItem.accountingPeriod.accountingPeriodId !== 'undefined' && this.selectedItem.accountingPeriod.accountingPeriodId > -1
      ) {

        if (this.selectedItem.account.accountType.accountType !== 'Savings') { //if account is not indicated as savings wipe out any attached savingsGoal
          this.selectedItem.savingsGoal = null;
        }

        this.$store.dispatch("createBudgetItem", this.selectedItem)
            .then(() => {
              this.loadInitData()
            });

        this.showOverlay = false;
      }
      else {
        console.log('invalid');
        this.alert = true; //show invalid form alert message
        setTimeout(() => { this.alert = false; }, 3000); //remove alert message after a time period

      }
    },
    updateItem() {
      //if validate is undefined we are making a commitment update from the table, auto validate this, data will already exist
      if (typeof this.$refs.form === 'undefined') {

        if (this.selectedItem.account.accountType.accountType !== 'Savings') { //if account is not indicated as savings wipe out any attached savingsGoal
          this.selectedItem.savingsGoal = {};
        }

        this.$store.dispatch("updateBudgetItem", this.selectedItem)
            .then(() => {
              this.$store.dispatch("fetchAccounts")
            });
      }
      else {
        const valid = this.$refs.form.validate();

        if (valid) {
          this.$store.dispatch("updateBudgetItem", this.selectedItem)
              .then(() => {
                this.loadInitData();
              });
        }
        else {
          console.log('invalid');
          this.alert = true; //show invalid form alert message
          setTimeout(() => { this.alert = false; }, 3000); //remove alert message after a time period
        }
        this.showOverlay = false;
      }
    },
    deleteItem() {
      this.$store.dispatch("deleteBudgetItem", this.selectedItem)
          .then(() => {
            console.log('hereeeeeee');
            this.loadInitData();
          });
    },
    deleteItemConfirm(item) {
      console.log(item);
      this.selectedItem = item;

      if (item.committed) {
        console.log('delete alerty ');
        this.deleteAlert = true;
      }

      this.deleteConfirmOverlay = true;
    },
    isItemCommitted(item) {
      return item.committed;
    },
    isItemAccountTransfer(item) {
      return item.budgetType.type === 'Account Transfer';
    },
    updateFormForItemCreation(selectedAccountingPeriodIndex) {
      this.createFormActive = true;
      const today = new Date();

      let displayedAccountingPeriod;

      //setting the accounting period select box selection on the budget item form based on the user selected month in the tab array
      if (typeof this.selectedAccountingPeriods.accountingPeriods !== 'undefined') {
        displayedAccountingPeriod = this.selectedAccountingPeriods.accountingPeriods[selectedAccountingPeriodIndex];
      }
      else {
        const accountingPeriodMonths = this.$store.getters.getAccountingPeriodMonths;
        const selectedAccountingPeriods = accountingPeriodMonths[today.getMonth()].accountingPeriods;
        displayedAccountingPeriod = selectedAccountingPeriods[selectedAccountingPeriodIndex];
      }

      this.selectedItem = {
        "name": "",
        "user": this.$store.getters.getUser,
        "budgetType": {
          "budgetTypeId": 1,
          "type": "Car Payment"
        },
        "account": {},
        "savingsGoal": {},
        "accountingPeriod": displayedAccountingPeriod,
        "createdDate": today
      };
    },
    updateAccountingPeriodNotes(item) {
      console.log('blurring!!!');
      console.log(item);

      this.$store.dispatch("updateAccountingPeriod", item)
          .then(() => {
            console.log('hereeeeeee');
            // this.loadInitData();
          });
    },
    test(tab) {
      this.selectedAccountingPeriods = tab;
    },
    userDisplaySwitch() {
      this.loadInitData();
    }
  },
}
</script>