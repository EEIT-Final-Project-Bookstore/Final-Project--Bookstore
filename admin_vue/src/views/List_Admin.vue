<template>
  <div class="adminList-container">
    <h1>管理員列表</h1>
    <button @click="showCreateModal = true" class="btn-create">新增管理員</button>
    <button @click="showRankModal = true" class="btn-rank">查看管理階級</button>

    <table class="admin-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>帳號</th>
          <th>密碼</th>
          <th>電子郵件</th>
          <th>階級</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(admin, index) in admins" :key="index">
          <td>{{ admin.adminId }}</td>
          <td>{{ admin.adminAccount }}</td>
          <td>{{ admin.passwordPlain }}</td>
          <td>{{ admin.email }}</td>
          <td>
            <select v-model="admin.rankId" @change="updateAdminRank(admin)">
              <option v-for="rank in ranks" :key="rank.rankID" :value="rank.rankID">
                {{ rank.rankName }}
              </option>
            </select>
          </td>
          <td>
            <button class="btn-edit" @click="openEditModal(admin)">修改</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- 新增管理員 Modal -->
    <div v-if="showCreateModal" class="modal">
      <div class="modal-content">
        <h3>新增管理員</h3>
        <form @submit.prevent="createAdmin">
          <div class="form-group">
            <label>帳號</label>
            <input v-model="newAdminForm.adminAccount" type="text" required />
          </div>
          <div class="form-group">
            <label>電子郵件</label>
            <input v-model="newAdminForm.email" type="email" required />
          </div>
          <div class="form-group">
            <label>密碼</label>
            <input v-model="newAdminForm.password" type="text" required />
          </div>
          <div class="form-group">
            <label>階級</label>
            <select v-model="newAdminForm.rankId">
              <option v-for="rank in ranks" :key="rank.rankID" :value="rank.rankID">
                {{ rank.rankName }}
              </option>
            </select>
          </div>
          <div class="form-actions">
            <button type="submit" class="btn-save">送出</button>
            <button type="button" class="btn-cancel" @click="showCreateModal = false">取消</button>
          </div>
        </form>
      </div>
    </div>

    <!-- 修改管理員 Modal -->
    <div v-if="showEditModal" class="modal">
      <div class="modal-content">
        <h3>修改管理員</h3>
        <form @submit.prevent="updateAdmin">
          <div class="form-group">
            <label>ID</label>
            <input v-model="editForm.adminId" type="text" disabled />
          </div>
          <div class="form-group">
            <label>帳號</label>
            <input v-model="editForm.adminAccount" type="text" disabled />
          </div>
          <div class="form-group">
            <label>電子郵件</label>
            <input v-model="editForm.email" type="email" />
          </div>
          <div class="form-group">
            <label>密碼</label>
            <input v-model="editForm.passwordPlain" type="text" />
          </div>
          <div class="form-actions">
            <button type="submit" class="btn-save">儲存</button>
            <button type="button" class="btn-cancel" @click="showEditModal = false">取消</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";

export default {
  name: "AdminList",
  data() {
    return {
      admins: [],
      ranks: [],
      showCreateModal: false,
      showEditModal: false,
      newAdminForm: {
        adminAccount: "",
        email: "",
        password: "",
        rankId: null,
      },
      editForm: {
        adminId: null,
        adminAccount: "",
        email: "",
        passwordPlain: "",
        rankId: null,
      },
    };
  },
  created() {
    this.fetchAdmins();
    this.fetchRanks();
  },
  methods: {
    decodePassword(base64Password) {
      try {
        return atob(base64Password);
      } catch (e) {
        console.error("密碼解碼失敗", e);
        return "";
      }
    },
    encodePassword(password) {
      return btoa(password);
    },
    fetchAdmins() {
      axios
        .get("http://localhost:8080/api/admins")
        .then((res) => {
          this.admins = res.data.map((admin) => ({
            ...admin,
            passwordPlain: this.decodePassword(admin.password),
          }));
        })
        .catch((err) => {
          console.error("無法取得管理員列表:", err);
        });
    },
    fetchRanks() {
      axios
        .get("http://localhost:8080/api/ranks")
        .then((res) => {
          this.ranks = res.data;
        })
        .catch((err) => {
          console.error("無法取得階級列表:", err);
        });
    },
    createAdmin() {
      const encodedPassword = this.encodePassword(this.newAdminForm.password);

      axios
        .post("http://localhost:8080/api/admins", {
          adminAccount: this.newAdminForm.adminAccount,
          email: this.newAdminForm.email,
          password: encodedPassword,
          rankId: this.newAdminForm.rankId,
        })
        .then(() => {
          this.showCreateModal = false;
          this.newAdminForm = { adminAccount: "", email: "", password: "", rankId: null };
          this.fetchAdmins();
          Swal.fire({ title: "新增成功", icon: "success" });
        })
        .catch((err) => {
          console.error("新增管理員失敗:", err);
          Swal.fire({ title: "新增失敗", icon: "error" });
        });
    },
    openEditModal(admin) {
      this.editForm = {
        adminId: admin.adminId,
        adminAccount: admin.adminAccount,
        email: admin.email,
        passwordPlain: admin.passwordPlain,
        rankId: admin.rankId,
      };
      this.showEditModal = true;
    },
    updateAdmin() {
      const encodedPassword = this.encodePassword(this.editForm.passwordPlain);

      axios
        .put(`http://localhost:8080/api/admins/${this.editForm.adminAccount}`, {
          adminId: this.editForm.adminId,
          adminAccount: this.editForm.adminAccount,
          email: this.editForm.email,
          password: encodedPassword,
          rankId: this.editForm.rankId,
        })
        .then(() => {
          this.showEditModal = false;
          this.fetchAdmins();
          Swal.fire({ title: "修改成功", icon: "success" });
        })
        .catch((err) => {
          console.error("更新管理員失敗:", err);
          Swal.fire({ title: "修改失敗", icon: "error" });
        });
    },
    updateAdminRank(admin) {
      const encodedPassword = this.encodePassword(admin.passwordPlain || "");

      axios
        .put(`http://localhost:8080/api/admins/${admin.adminAccount}`, {
          adminId: admin.adminId,
          adminAccount: admin.adminAccount,
          email: admin.email,
          password: encodedPassword,
          rankId: admin.rankId,
        })
        .then(() => {
          Swal.fire({ title: "階級已更新", icon: "success", timer: 1000, showConfirmButton: false });
        })
        .catch((err) => {
          console.error("更新階級失敗:", err);
          Swal.fire({ title: "更新階級失敗", icon: "error" });
        });
    },
  },
};
</script>

<style scoped>
.adminList-container {
  font-family: Arial, sans-serif;
  margin: 0 auto;
  padding: 20px;
  max-width: 1200px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.site-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.site-header .logo img {
  max-height: 50px;
}

.admin-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}

.admin-table th,
.admin-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

.admin-table th {
  background-color: #f4f4f4;
  font-weight: bold;
}

.admin-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.admin-table tr:hover {
  background-color: #f1f1f1;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  width: 400px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-group input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.form-actions {
  display: flex;
  justify-content: space-between;
}

.btn-edit {
  background-color: #007bff;
  color: white;
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-delete {
  background-color: #dc3545;
  color: white;
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-save {
  background-color: #28a745;
  color: white;
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-cancel {
  background-color: #6c757d;
  color: white;
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>
