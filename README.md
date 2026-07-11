# 🔐 RanmcLogin — Minecraft Fabric 自动登录 Mod

一个轻量级的 Fabric Mod，进入指定服务器后自动执行登录，让你无需手动输入密码，即开即玩！🎮

## ✨ 功能特性

- 🔑 **自动登录** — 加入服务器后自动发送 `/l <密码>` 指令
- 📊 **自动查询 TPS** — 登录后自动执行 `/tps`
- 💀 **自动重生** — 检测到死亡界面时自动重生后登录
- ⚙️ **可视配置** — 通过 `ranmclogin.json` 配置文件管理账号密码和服务器列表
- 🪶 **轻量无依赖** — 只需 Fabric API，无多余依赖

## 📥 安装要求

- **Minecraft**: 26.1.1
- **Fabric Loader**: ≥ 0.18.6
- **Fabric API**: ≥ 0.145.3+26.1.1
- **Java**: ≥ 26

## 🔧 安装方法

1. 从 [Releases](https://github.com/FabricMC/fabric-example-mod/releases) 下载最新版本的 `RanmcLogin` jar 文件
2. 将 jar 文件放入 Minecraft 的 `mods` 文件夹
3. 确保 `fabric-api` 也已安装在 `mods` 文件夹中
4. 启动游戏，模组会自动生成配置文件

## 📝 配置文件

首次运行后，配置文件会自动生成在：
```
.minecraft/config/ranmclogin.json
```

### 配置示例

```json
{
  "accounts": {
    "你的游戏名": "你的密码"
  },
  "servers": [
    "ranmc.cc",
    "mc9.city"
  ]
}
```

## 📄 开源协议

本项目基于 [CC0-1.0](./LICENSE) 协议开源。
