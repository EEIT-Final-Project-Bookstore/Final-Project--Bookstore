<template>
        <div class="admin-manage-images">
            <h1>首頁圖片處理</h1>
            <!--查詢區-->
<div class="search-bar">
    <input v-model="searchBookId" type="text" placeholder="請輸入書籍 ID" />
    <button @click="fetchSingleImage">查詢圖片</button>
    <button @click="fetchImages(true)">顯示全部</button>
</div>


<!--圖片列表-->
<ul class="image-list">
    <li v-for="image in images" :key="image.id" class="image-item">
        <p>流水號:{{image.id}}</p>
        <p>圖片ID:{{image.imageID}}</p>
        <p>書籍ID:{{image.bookId}}</p>
        <button @click="deleteImage(image.bookId)">刪除</button>
        <button @click="openModal('edit',image)">編輯</button>
    </li>
</ul>
<!--新增圖片按鈕-->
<button @click="openModal('add')" class="add-image-button">新增圖片</button>
<!--Modal-->
   <div v-if="showModal" class="modal">
    <div class="modal-content"><h2>{{ modalMode==='add' ? '新增圖片' : '編輯圖片' }}</h2><form @submit.prevent="submitModal">
      <label>
        書籍ID: <input v-model="modalData.bookId" required />
      </label>
      <label>
        圖片ID: <input v-model="modalData.imageID" required />
      </label>
      <button type="submit">{{ modalMode === 'add' ? '新增' : '保存' }}</button>
      <button type="button" @click="closeModal">取消</button>
    </form></div>
   </div>

        </div>          
</template>
    
<script setup>
    import { fetchAllHomepageImages,deleteHomepageImage,updateHomepageImage,addHomepageImage, fetchHomepageImagesBookId } from '../api/HomepageApi';
    import {ref,reactive,onMounted} from 'vue'
    

    
 
    const images = ref([])
    const searchBookId = ref("")
    const showModal =ref(false)
    const modalMode = ref("add")//add或edit
    const modalData = reactive({
        id:'',
        bookId:'',
        imageID:'',
        URL:''
    })
    //查詢圖片數據
    const fetchImages = async (showAll = false) => {
        try{
            const response = showAll ? await fetchAllHomepageImages() :await fetchAllHomepageImages({bookId:searchBookId.value
            })
            console.log('API 返回的數據:', response.data);
            if (response && Array.isArray(response)) {
      images.value = response; // 確保數據正確賦值
    } else {
      console.warn("返回的數據格式不正確或為空");
      images.value = []; // 防止數據異常導致 UI 問題
    }
        }catch(error){
            console.error('獲取圖片失敗',error)
        }
    }

    const fetchSingleImage = async() => {
      try{
        if(!searchBookId.value.trim()){
          console.warn("請輸入書籍後再查詢")
          return
        }
        const response = await fetchHomepageImagesBookId(searchBookId.value.trim());
        images.value = [response];
        console.log("查詢書籍單筆數據",response)
      }catch(error){
        console.error("查詢單筆圖片失敗",error);
      }
    }
    //刪除圖片
    const deleteImage = async (bookId) =>{
        try{
          await deleteHomepageImage(bookId);
          fetchImages();//刪除後刷新列表
        }catch(error){
            console.error('刪除圖片失敗',error)
        }
    
      }
   
//打開Modal
const openModal = (mode,image=null) => {
  modalMode.value = mode;
  showModal.value = true;
  if(mode === 'edit' && image){
    modalData.id = image.id;
    modalData.bookId = image.bookId;
    modalData.imageID = image.imageID;
  }else{
    modalData.id = '';
    modalData.bookId = '';
    modalData.imageID = '';
  }
}

//關閉Modal
const closeModal = () => {
  showModal.value = false;
}

//提交Modal表單
const submitModal = async () => {
  try{
    if(!modalData.bookId || !modalData.imageID){
      console.error('書籍ID和圖片ID為必填')
      return
    }

    //構建後端需要的數據結構
    const requestData = {
    imageID: Number(modalData.imageID),//確保是數字
    book: {
        bookId: Number(modalData.bookId)//確保是數字
    }
  }

    console.log("提交的數據:",requestData)//檢查格式

    if(modalMode.value === 'add'){
      await addHomepageImage(requestData);
    }else{
      await updateHomepageImage(modalData.bookId,requestData)
    }
    fetchImages();
    closeModal();
  }catch(error){
    console.log(`${modalMode.value === 'add' ?'新增' : '修改'}圖片失敗`,error);
  }
}


    //組件掛載時自動獲取數據
    onMounted(()=>fetchImages(true))
    

   


</script>
    
<style scoped>
.admin-manage-images {
  padding: 20px;
}

.search-bar {
  margin-bottom: 20px;
}

.image-list {
  list-style-type: none;
  padding: 0;
}

.image-item {
  margin-bottom: 15px;
  border-bottom: 1px solid #ccc;
  padding-bottom: 10px;
}

.add-image-button {
  margin-top: 20px;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-content {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  width: 300px;
}  
</style>