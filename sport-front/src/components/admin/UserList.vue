<template>
  <div>
    用户列表
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">
        首页
      </el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!--用户列表主体-->
    <el-card>  
      <el-row :gutter="25"> 
        <!--搜索区域-->
        <!--搜索添加-->
        <el-col :span="10"> 
          <el-input
            v-model="queryInfo.query"
            placeholder="请输入搜索内容"
            clearable
            @clear="getUserList"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getNewUserList"
            />
          </el-input>
        </el-col>
        <!--搜索按钮-->
        <el-col :span="4">
          <el-button
            type="primary"
            @click="addDialogVisible = true"
          >
            添加用户
          </el-button>
        </el-col>
      </el-row>
      <!--用户列表-->
      <el-table
        :data="userList"
        border
        stripe
      >
        <el-table-column type="index" />
        <el-table-column
          label="用户名"
          prop="username"
        />
        <el-table-column
          label="邮箱"
          prop="email"
        />
        <el-table-column
          label="密码"
          prop="password"
        />
        <el-table-column
          label="角色"
          prop="role"
        />
        <el-table-column
          label="状态"
          prop="state"
        >
          <!--作用域插槽-->
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.state"
              @change="userStateChanged(scope.row)"
            />
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <!-- 修改 -->
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="showEditDialog(scope.row.id)"
            />
            <!-- 删除 -->
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="deleteUser(scope.row.id)"
            />
            <!-- 权限 -->
            <el-tooltip
              effect="dark"
              content="分配权限"
              placement="top-start"
              :enterable="false"
            >
              <!--文字提示 enterable 隐藏-->
              <el-button
                type="warning"
                icon="el-icon-setting"
                size="mini"
              />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <!--分页-->
      <div>
        <el-pagination
          :current-page="queryInfo.pageNum"
          :page-sizes="[1, 2, 5, 100]"
          :page-size="queryInfo.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
    <!--新增用户区域-->
    <el-dialog
      title="添加用户"
      :visible.sync="addDialogVisible"
      width="50%"
      @close="addDialogClosed"
    >
      <el-form
        ref="addFormRef"
        :model="addForm"
        :rules="addFormRules"
        label-width="70px"
      >
        <!--用户名-->
        <el-form-item
          label="用户名"
          prop="username"
        >
          <el-input v-model="addForm.username" />
        </el-form-item>
        <!--密码-->
        <el-form-item
          label="密码"
          prop="password"
        >
          <el-input v-model="addForm.password" />
        </el-form-item>
        <!--邮箱-->
        <el-form-item
          label="邮箱"
          prop="email"
        >
          <el-input v-model="addForm.email" />
        </el-form-item>
      </el-form>
      <!--内容底部区域-->
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button
          type="primary"
          @click="addUser"
        >确定</el-button>
      </span>
    </el-dialog>
    <!--修改对话框-->
    <el-dialog
      title="修改用户信息"
      :visible.sync="editDialogVisible"
      width="50%"
      @close="editDialogClosed"
    >
      <el-form
        ref="editFormRef"
        :model="editForm"
        :rules="editFormRules"
        label-width="70px"
      >
        <!--用户名-->
        <el-form-item
          label="用户名"
          prop="username"
        >
          <el-input
            v-model="editForm.username"
            disabled
          />
        </el-form-item>
        <!--密码-->
        <el-form-itemlabel="密码" prop="password">
          <el-input v-model="editForm.password" />
          </el-form-itemlabel=>
          <!--邮箱-->
          <el-form-item
            label="邮箱"
            prop="email"
          >
            <el-input v-model="editForm.email" />
          </el-form-item>
        </el-form-itemlabel="密码">
      </el-form>
      <!--内容底部区域-->
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button
          type="primary"
          @click="editUserInfo"
        >确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
    data(){
          return{
            //查询信息实体
         queryInfo:{
            query:"",//查询信息
            pageNum: 1,//当前页
            pageSize: 5,//每页最大数
         },
         userList:[],//用户列表
         total: 0,//总记录数
         addDialogVisible:false,//对话框状态
         //添加对话框
         addForm:{
          username:'',
          password:'',
          email:''
         },
         //修改用户信息
         editForm:{
          
         },
         //显示/隐藏修改用户栏
         editDialogVisible:false,
         //添加表单验证
         addFormRules:{
          username:[
            {required: true, message:"请输入用户名",trigger:"blur"},
            {min:5,max: 8,message:"长度在5到8个字符之间",trigger:"blur"}
          ],
          password:[
            {required: true, message:"请输入密码",trigger:"blur"},
            {min: 6,max: 8,message:"长度在6到8个字符之间",trigger:"blur"}
          ],
          email:[
            {required: true, message:"请输入邮箱",trigger:"blur"},
            {min: 5,max: 15,message:"请输入正确的邮箱地址",trigger:"blur"}
          ]
         },
          //修改表单验证
          editFormRules:{
            password:[
            {required: true, message:"请输入密码",trigger:"blur"},
            {min: 6,max: 8,message:"长度在6到8个字符之间",trigger:"blur"}
          ],
          email:[
            {required: true, message:"请输入邮箱",trigger:"blur"},
            {min: 5,max: 15,message:"请输入正确的邮箱地址",trigger:"blur"}
          ]
          },
          }
    },
    created(){
      this.getUserList();
    },

    methods: {
        //获取所有用户信息
       async getUserList(){
             const{data:res} =  await this.$http.get("alluser",{params:this.queryInfo});
             this.userList =res.data;
             this.total = res.numbers;
 
        },
         getNewUserList(){
           this.queryInfo.pageNum = 1;
           this.getUserList();
         },
        handleSizeChange(newSize){
          this.queryInfo.pageSize = newSize;
          this.getUserList();
        },
        //pageNum的触发动作
        handleCurrentChange(newPage){
         this.queryInfo.pageNum = newPage;
         this.getUserList();
        },
       async userStateChanged(userInfo){
         const {data:res} = await this.$http.put(`userstate?id=${userInfo.id}&state=${userInfo.state}`);
         if(res!="success"){
          userInfo.id = !userInfo.id
          return this.$message.error("操作失败！！！");
         }
         this.$message.success("操作成功！！！");
        },
        //监听用户操作
        addDialogClosed(){
         this.$refs.addFormRef.resetFields();
        },
        addUser(){
          this.$refs.addFormRef.validate(async valid=>{
            if(!valid) return;
            const {data:res} = await this.$http.post("adduser",this.addForm);
            if(res!="success"){
              return this.$message.error("操作失败！！！");
            }
            this.$message.success("操作成功！！！");
            this.addDialogVisible = false;
            this.getUserList();
          })
        },
        //根据主键删除信息
        async deleteUser(id){
          const confirmResult = await this.$confirm('此操作将永久删除用户信息,是否继续?','提示',{
              confirmButtonText:'确定',
              cancelButtonText:'取消',
              type:'warning'
            }).catch(err => err)
            if(confirmResult!='confirm'){
              return this.$message.info("已取消删除");
            }
          const {data:res} = await this.$http.delete("deleteUser?id="+id);
            if(res !="success"){
            return this.$message.error("删除失败");
            }
            this.$message.success("删除成功！");
            this.getUserList();
            
        },
        //显示对话框
       async showEditDialog(id){
          const {data:res} = await this.$http.get("getupdate?id="+id);
          this.editForm = res;//查询用户信息反填回编辑表单
          this.editDialogVisible = true ;//开启编辑对话框
    },
    //关闭窗口
    editDialogClosed(){
      this.$refs.editFormRef.resetFields();//重置信息
    },
    //确认修改
    editUserInfo(){
    this.$refs.editFormRef.validate(async valid =>{
      if(!valid) return;
      //发起修改请求
    const {data:res} = await  this.$http.put("edituser",this.editForm);
    if(res!="success") return this.$message.error("操作失败！");
    this.$message.success("操作成功！");
    //隐藏对话框
    this.editDialogVisible = false;
    this.getUserList();
    })
    },
}
}
</script>

<style lang="less" scoped>
.el-breadcrumb-item{
    margin-bottom: 15px;
    font-size: 17px;
}
</style>