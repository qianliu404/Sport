<template>
  <el-container class="home-container">
    <el-header>
      <div>
        <img
          src="../assets/logo.jpg"
          alt
        >
        <span>个人运动平台</span>
      </div>
      <el-button
        type="info"
        @click="logout"
      >
        安全退出
      </el-button>
    </el-header>
    <el-container>
      <!--侧边栏-->
      <el-aside :width="isCollapse ?'64px':'200px'">
        <!--伸缩按钮-->
        <div
          class="toggle-button"
          @click="toggleCollapase"
        >
          |||
        </div>
        <!--侧边栏菜单区 unique-opened="true" 只保持一个菜单展开 router开启路由-->
        <el-menu
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#409eff"
          unique-opened
          :collapse="isCollapse"
          :collapse-transition="false"
          :router="true"
          default-active="activePath"
        >
          <el-submenu
            v-for="item in menuList"
            :key="item.id"
            :index="item.id+''"
          >
            <template slot="title">
              <i :class="iconsObject[item.id]" />
              <span>{{ item.title }}</span>
            </template>

            <el-menu-item
              v-for="it in item.sList"
              :key="it.id"
              :index="it.path"
              @click="saveNavState()"
            >
              <template slot="title">
                <i :class="iconsObject[it.id]" />
                <span>{{ it.title }}</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data(){
          return{
            //菜单列表
            menuList:[],
            isCollapse:false,
            iconsObject:{
              '100':'iconfont icon-guanliyuan',
              '200':'iconfont icon-yundong',
              '101':'iconfont icon-denglu',
              '102':'iconfont icon-mima',
              '103':'iconfont icon-yundong',
              '104':'iconfont icon-shangpin',
              '201':'iconfont icon-shu',
              '202':'iconfont icon-qialuli',
              '203':'iconfont icon-shiwu',
              '204':'iconfont icon-denglu',
    
            }  ,
            activePath:'/user',//默认路径
          }
          },
    //onload事件
   created(){
       //查询menuList
       this.getMenuList();
       this.activePath = window.sessionStorage.getItem('activePath');//取出session里的path 
   },

    methods:{
        logout(){
            window.sessionStorage.clear();//清除session
            this.$router.push("/login");//回到首页

        },
        //获取导航菜单方法
      async  getMenuList(){
    const{data:res} = await this.$http.get("menus");
       if(res.flag != 200) return this.$message.error("获取列表信息失败!!!");
          this.menuList = res.menus;
        },
        //控制伸缩
        toggleCollapase(){
          this.isCollapse = !this.isCollapse;
        },
        //保存路径
        saveNavState(activePath){
          window.sessionStorage.setItem('activePath',activePath);//存放在session中
          this.activePath = activePath;
        }
    }
}
</script>

<style lang="less" scoped>
    .home-container{
        height: 100%;
    }
    .el-header{
        background-color: #373d41;
        display: flex;
        justify-content: space-between;
        padding-left: 0%;
        color: #fff;
        font-size: 20px;
        >div{
        display: flex;
        align-items: center;
        span{
            margin-left:15px ;
        }
    }
    }
    .el-aside{
        background-color: #333744;
        .el-menu{
          border-radius: none;
        }
    }
    .el-main{
        background-color: #eaedf1;
       
    }
    img{
        width:55px;
        height: 55px;
    }
    .toggle-button{
      background-color: #4A5064;
      font-size: 10px;
      line-height: 24px;
      color:#fff;
      text-align: center;
      letter-spacing: 0.2em;
      cursor: pointer;
    }
</style>