FROM node:16-alpine as build-stage

WORKDIR /admim

COPY .npmrc package.json yarn.lock ./
RUN yarn install --frozen-lockfile

COPY . .

# 构建命令
ARG NODE_ENV
RUN if [ "$NODE_ENV" = "production" ]; then \
      yarn build:prod; \
    else \
      yarn build; \
    fi

## -- nginx --
FROM nginx:alpine

ENV TZ=Asia/Shanghai

COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build-stage /admim/dist /usr/share/nginx/html

EXPOSE 80