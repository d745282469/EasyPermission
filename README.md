# EasyPermission使用指南

## 简单使用

1. 在项目最外层的**build.gradle**中声明

   ```java
   allprojects {
       repositories {
           google()
           jcenter()
           maven { url 'https://www.jitpack.io' }
       }
   }
   ```

2. 在项目Module，一般名为app目录下的**build.gradle**中引入该库

   ```css
   implementation 'com.github.d745282469:EasyPermission:1.1'
   ```

3. 在AndroidManifest.xml中声明需要使用的权限

4. 构造一个List，传入需要动态申请的权限

   ```kotlin
   val permissions = ArrayList<String>()
   permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
   permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
   permissions.add(Manifest.permission.ACCESS_WIFI_STATE)
   ```

5. 调用EasyPermission

   ```kotlin
   EasyPermission
       .inActivity(this)
       .permissions(permissions)
       .beforeGranted(object :BeforeGrantedListener{
           override fun beforeGranted(definedPermissions: List<String>) {
               Log.d("dong","准备申请下列权限：${definedPermissions.toTypedArray().contentToString()}")
           }
       })
       .onDefined(object :OnDefinedListener{
           override fun onDefined(definedPermissions: List<String>) {
               Log.e("dong","下列权限被拒绝：${definedPermissions.toTypedArray().contentToString()}")
           }
       })
       .onGranted(object :OnGrantedListener{
           override fun onGranted(grantedPermissions: List<String>) {
               Log.d("dong","部分权限被通过：${grantedPermissions.toTypedArray().contentToString()}")
           }
   
           override fun onGrantedAll(grantedPermissions: List<String>) {
               Log.d("dong","全部权限被通过：${grantedPermissions.toTypedArray().contentToString()}")
           }
       })
       .granted()
   ```



## 监听器

### BeforeGrantedListener

正式发起授权前会被调用。

```kotlin
/**
 * @param definedPermissions 实际会提示用户授权的权限
 * 因为有些权限其实是不会调起授权窗口的，或者有些权限已经被通过了
 */
fun beforeGranted(definedPermissions:List<String>)
```

### OnDefinedListener

有权限被拒绝时调用。**该监听器可能与`OnGrantedListener.onGranted()`同时被调用**。

```kotlin
/**
 * @param definedPermissions 被拒绝的权限集合
 */
fun onDefined(definedPermissions:List<String>)
```

### OnGrantedListener

权限被通过时调用，**`onGranted()`可能与`OnDefinedListener.onDefined`同时被调用**。

```kotlin
/**
 * 部分权限通过，此时OnDefinedListener也会被调用，因为部分权限被拒绝了
 * @param grantedPermissions 被授予的权限集合
 */
fun onGranted(grantedPermissions:List<String>)

/**
 * 所有申请的权限都被通过了
 * @param grantedPermissions 被授予的权限集合
 * */
fun onGrantedAll(grantedPermissions: List<String>)
```