export default {
  methods: {
    beforeVideoUpload(file) {
      let data = {
        status:false,
        fileType:undefined,
        uploadKey:undefined,
        uploadName:undefined
      }
      let suffix = file.name.substring(file.name.lastIndexOf('.') + 1).toLowerCase();
      data.fileType = suffix;
      const allowedSuffixes = ['3gp', 'avi', 'flv', 'mp4', 'vob', 'mpg', 'asf', 'wmv', 'mkv', 'mov'];
      if (!allowedSuffixes.includes(suffix)) {
        this.$message.error("文件类型错误");
        data.status = false; // 失败则返回true
        return data;
      }
      
      if (file.size > 5 * 1024 * 1024 * 1024) { // 5GB
        this.$message.error("文件不能超过5GB");
        data.status = false; // 失败则返回true
        return data;
      }

      if (file.name.length > 150) {
        this.$message.error("文件名过长");
        data.status = false; // 失败则返回true
        return data;
      }
      const generateRandomString = (length) => {
        const characters = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
        return Array.from({ length }, () => characters.charAt(Math.floor(Math.random() * characters.length))).join('');
      };
      const uniq = generateRandomString(10) + Date.now();
      data.uploadKey = `/lesson/video/${uniq}.${suffix}`;
      data.uploadName = `${uniq}.${suffix}`;
      data.status = true; // 成功则返回true
      return data; 
    },
  }
};