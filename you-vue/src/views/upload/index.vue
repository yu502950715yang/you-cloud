<template>
  <div>
    <el-upload
      :headers="headers"
      class="upload-demo"
      action="/api/core/upload/ftpUpload"
      :on-preview="handlePreview"
      :on-remove="handleRemove"
      :before-remove="beforeRemove"
      multiple
      :limit="3"
      :on-exceed="handleExceed"
      :file-list="fileList">
      <el-button size="small" type="primary">文件上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
    </el-upload>
    <el-upload
      :headers="headers"
      action="/api/core/upload/ftpUpload"
      list-type="picture-card"
      :on-preview="ftpHandlePreview"
      :on-remove="handleRemove">
      <i class="el-icon-plus"></i>
    </el-upload>
  </div>
</template>
<script>
  import {ftpUpload} from '~/system/upload'
  import {getToken} from '@/utils/auth'

  export default {
    data() {
      return {
        fileList: [],
        headers: {
          token: getToken()
        }
      };
    },
    methods: {
      handleRemove(file, fileList) {
        //console.log(file, fileList);
      },
      ftpHandlePreview(file) {
        ftpUpload(file).then(res => {
          this.$message({
            type: 'success',
            message: '提交成功!'
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '提交失败!'
          })
        })
        console.log(file);
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`);
      }
    }
  }
</script>
