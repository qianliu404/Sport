<template>
  <div class="login_container">
    <div class="login_box">
      <div class="avatar_box">
        <img
          src="../assets/logo.png"
          alt
        >
      </div>
      <el-form
        ref="loginFormRef"
        :model="loginForm"
        :rules="loginRules"
        class="login_form"
        label-width="0px"
      >
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            prefix-icon="iconfont icon-denglu"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            prefix-icon="iconfont icon-mima"
            type="password"
          />
        </el-form-item>
        <el-form-item class="btns">
          <el-button
            type="primary"
            @click="login"
          >
            登录
          </el-button>
          <el-button
            type="info"
            @click="resetLoginForm"
          >
            重置
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data(){
    return{
      loginForm:{
        username:"admin",
        password:"123456"
      },
      loginRules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 5, max: 8, message: "长度在 5 到 8 个字符", trigger: "blur" }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, max: 8, message: "密码为 6~8 位", trigger: "blur" }
        ]
    }
  }
},
  methods:{
    //重置表单内容
       resetLoginForm(){
        this.$refs.loginFormRef.resetFields();
       },
       login(){
       this.$refs.loginFormRef.validate(async valid =>{
        if(!valid) return;//验证失败
      const {data:res} = await this.$http.post("login",this.loginForm); //访问后台
        if(res.flag =="ok"){
          window.sessionStorage.setItem("user",res.user);//存储user对象
          this.$message.success("操作成功！！！"); //信息提示
          this.$router.push({path:"/home"});//页面路由跳转
        }else{
          this.$message.error("操作失败！！！");//错误提示
        }
       })
       },
      }
      
}
</script>

<style lang="less" scoped>
  .login_container{
    background-color: #2064a7;
    height: 100%;
    
  } 
  .login_box{
    width: 450px;
    height: 300px;
    background-color: rgb(236, 241, 237);
    border-radius: 3px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%,-50%);
    .avatar_box{
      width: 130px;
      height: 130px;
      border: 1px solid #eee;
      border-radius: 50% ;
      padding: 10px;
      box-shadow: 0 0 10px #ddd;
      position: absolute;
      left: 50%;
      transform: translate(-50%,-50%);
      background-color: #0ee;
      img{
        width: 100%;
        height: 100%;
        border-radius: 50%;
        background-color: #eee;
      }
    }
  }
  .btns{
    display: flex;
    justify-content: flex-end;
  }
  .login_form{
   position: absolute;
   bottom: 0%;
   width: 100%;
   padding: 0 10px;
   box-sizing: border-box;
  }
</style>