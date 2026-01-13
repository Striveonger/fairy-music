<template>
    <div class="login-container">
        <div class="login-card">
            <div class="login-header">
                <h2>Fairy Music</h2>
                <p>用户登录</p>
            </div>

            <form @submit.prevent="handleLogin" class="login-form">
                <div class="form-group">
                    <label for="username">用户名</label>
                    <input
                        id="username"
                        v-model="form.username"
                        type="text"
                        class="form-control"
                        placeholder="请输入用户名"
                        required
                        :disabled="loading"
                    />
                </div>

                <div class="form-group">
                    <label for="password">密码</label>
                    <input
                        id="password"
                        v-model="form.password"
                        type="password"
                        class="form-control"
                        placeholder="请输入密码"
                        required
                        :disabled="loading"
                    />
                </div>

                <div v-if="errorMessage" class="alert alert-danger" role="alert">
                    {{ errorMessage }}
                </div>

                <button type="submit" class="btn btn-primary w-100" :disabled="loading">
                    <span v-if="loading" class="spinner-border spinner-border-sm me-2"></span>
                    {{ loading ? '登录中...' : '登录' }}
                </button>
            </form>

            <div class="login-footer">
                <p>还没有账号？ <router-link to="/register">立即注册</router-link></p>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/store/user';

const router = useRouter();
const authStore = useAuthStore();

const form = ref({
    username: '',
    password: ''
});

const loading = ref(false);
const errorMessage = ref('');

const handleLogin = async () => {
    errorMessage.value = '';
    loading.value = true;

    try {
        await authStore.login({
            username: form.value.username,
            password: form.value.password
        });
        // 登录成功后跳转到首页
        router.push('/');
    } catch (error: any) {
        errorMessage.value = error.response?.data?.message || '登录失败，请检查用户名和密码';
    } finally {
        loading.value = false;
    }
};
</script>

<style scoped lang="scss">
.login-container {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    padding: 20px;
}

.login-card {
    background: white;
    border-radius: 16px;
    box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
    width: 100%;
    max-width: 400px;
    padding: 40px;
}

.login-header {
    text-align: center;
    margin-bottom: 30px;

    h2 {
        font-size: 28px;
        font-weight: bold;
        color: #333;
        margin-bottom: 8px;
    }

    p {
        color: #666;
        font-size: 14px;
        margin: 0;
    }
}

.login-form {
    .form-group {
        margin-bottom: 20px;

        label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
        }

        .form-control {
            width: 100%;
            padding: 12px 16px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 14px;
            transition: all 0.3s;

            &:focus {
                border-color: #667eea;
                box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
                outline: none;
            }

            &:disabled {
                background-color: #f5f5f5;
                cursor: not-allowed;
            }
        }
    }

    .alert {
        padding: 12px 16px;
        border-radius: 8px;
        font-size: 14px;
        margin-bottom: 20px;
    }

    .btn {
        padding: 12px;
        font-size: 16px;
        font-weight: 500;
        border-radius: 8px;
        border: none;
        transition: all 0.3s;

        &:hover:not(:disabled) {
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
        }

        &:disabled {
            opacity: 0.6;
            cursor: not-allowed;
        }
    }
}

.login-footer {
    text-align: center;
    margin-top: 24px;
    padding-top: 24px;
    border-top: 1px solid #eee;

    p {
        color: #666;
        font-size: 14px;
        margin: 0;

        a {
            color: #667eea;
            text-decoration: none;
            font-weight: 500;

            &:hover {
                text-decoration: underline;
            }
        }
    }
}
</style>
