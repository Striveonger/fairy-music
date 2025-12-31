# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

Fairy-Music (fm) 是一个**自由的音乐播放器项目**，主要从B站获取音乐资源。项目名称源于游戏《绝区零》中的人工智能 "Fairy"，旨在解决音乐版权和会员限制问题。

### 已实现功能
- [x] 用户登录
- [x] 音乐搜索
- [x] 音乐播放
- [x] 收藏功能

## 技术架构

### 前端 (Vue 3 + TypeScript + Bootstrap 5)
- **框架**: Vue 3 + Composition API
- **UI框架**: Bootstrap 5
- **状态管理**: Pinia
- **构建工具**: Vite 5.3.1
- **包管理器**: pnpm
- **开发端口**: 5023
- **Base路径**: `/fm/`

### 后端 (Spring Boot 3 + MyBatis-Flex)
- **框架**: Spring Boot 3
- **数据库**: MySQL
- **ORM**: MyBatis-Flex
- **版本控制**: Flyway
- **API路径**: `/fm/api/v1`

### 音乐源
- **数据源**: Bilibili (B站)
- **爬虫**: Jsoup
- **模块化设计**: 支持扩展其他音乐平台

## 开发环境命令

### 前端开发 (website/)
```bash
cd website

# 安装依赖 (使用 pnpm)
pnpm install

# 启动开发服务器
pnpm run dev
# 访问: http://localhost:5023

# 构建生产版本
pnpm run build

# 预览构建结果
pnpm run preview
```

### 后端开发
```bash
# 清理并编译 (跳过测试)
mvn clean package -DskipTests

# 运行应用
java -jar target/fairy-music.jar
```

### 完整构建和部署流程
```bash
# 执行完整构建流程
./ci-cd/build.sh

# 构建流程包含:
# 1. Maven后端构建
# 2. 前端构建
# 3. Docker镜像构建
# 4. Helm打包
# 5. Kubernetes部署
```

## 项目结构

```
fairy-music/
├── website/                    # 前端项目
│   ├── src/
│   │   ├── apis/              # API接口定义
│   │   ├── components/        # Vue组件
│   │   ├── store/             # Pinia状态管理
│   │   ├── router/            # Vue Router配置
│   │   ├── views/             # 页面视图
│   │   ├── types/             # TypeScript类型定义
│   │   └── assets/            # 静态资源
│   ├── public/                # 公共资源
│   ├── dist/                  # 构建输出
│   ├── package.json
│   └── vite.config.ts         # Vite配置
│
├── internal/                   # 后端项目
│   └── src/main/java/         # Java源码
│       └── com/striveonger/music/fairy/
│           ├── web/           # Web控制器层
│           ├── sources/       # 音乐源实现(B站)
│           └── ...
│
├── docs/                       # 项目文档
├── ci-cd/                      # CI/CD脚本
└── README.md
```

## 开发配置

### Vite开发服务器配置
- **端口**: 5023
- **主机**: 0.0.0.0 (允许外部访问)
- **API代理**: `/fm/api/v1` → `http://127.0.0.1/`

### 路径别名
- `@/` → `website/src/`

### 代理配置 (vite.config.ts)
```typescript
proxy: {
    "/fm/api/v1": {
        target: "http://127.0.0.1/",
        changeOrigin: true
    }
}
```

## 部署信息

- **生产路径**: `/fm/`
- **域名**: `music.striveonger.com`
- **容器编排**: Kubernetes
- **包管理**: Helm
- **CI/CD**: Jenkins
- **基础设施**: Kubernetes + containerd + Harbor + Ansible

## 开发约定

### 代码规范
1. **Git提交**: 使用英文提交信息
2. **前端**: TypeScript严格模式，确保类型安全
3. **后端**: Spring Boot最佳实践
4. **音乐源**: 模块化设计，便于扩展新平台
5. **枚举值**: 使用大写命名 (如: 播放模式枚举)

### 数据库版本控制
- 使用 **Flyway** 进行数据库迁移
- 所有数据库变更通过migration脚本管理

### API设计
- RESTful API设计原则
- 统一响应格式
- 版本控制: `/fm/api/v1`

## 环境要求

### 前端
- **Node.js**: 18+ (Vite 5要求)
- **包管理器**: pnpm (推荐)
- **包管理器版本**: pnpm 9.12.0

### 后端
- **Java**: 17+
- **Maven**: 3.x
- **MySQL**: 8.0+

### 依赖版本 (package.json)
```json
{
  "vue": "^3.5.10",
  "vite": "^5.3.1",
  "typescript": "^5.2.2",
  "bootstrap": "^5.3.3",
  "pinia": "^2.2.4"
}
```

## 快速开始

1. **启动后端服务**:
   ```bash
   cd internal
   mvn clean package -DskipTests
   java -jar target/fairy-music.jar
   ```

2. **启动前端服务**:
   ```bash
   cd website
   pnpm install
   pnpm run dev
   ```

3. **访问应用**:
   - 前端: http://localhost:5023
   - API: http://localhost:5023/fm/api/v1

## 重要说明

- **开发模式**: 前端通过Vite代理访问后端API
- **生产模式**: 前端构建后部署到同一域名下，后端提供API服务
- **音乐源**: 目前仅支持B站，可扩展其他音乐平台
- **数据持久化**: MySQL存储用户数据、收藏等