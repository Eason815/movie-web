import requests
import pymysql
import time
from lxml import etree
import re
import random

conn = pymysql.connect(host="localhost", port=3306, user="root", password="qaz781212", database="moviedb")

def generate_random_datetime():
    year = 2024
    month = 6
    day = random.randint(1, 30)  # 假设六月份有30天
    hour = random.randint(0, 23)
    minute = random.randint(0, 59)
    second = random.randint(0, 59)
    return f"{year}-{month:02d}-{day:02d} {hour:02d}:{minute:02d}:{second:02d}"

with conn.cursor() as cursor:
    query = "select movie_id from movie"
    cursor.execute(query)
    movie_ids = cursor.fetchall()
    movie_ids = [id[0] for id in movie_ids]
    
    for movie_id in movie_ids:
        num = 250 - movie_id
        while num > 0:
            sql = "insert into movie_like(like_time, movie_id) values (%s, %s)"
            cursor.execute(sql, (generate_random_datetime(), str(movie_id)))
            conn.commit()

            num -= 1



    # #不存在这个staff，先创建
    # if cursor.fetchone() == None:
    #     sql = "insert into staff(name) values(%s)"
    #     cursor.execute(sql, staff)
    #     conn.commit()
    # query = "select staff_id from staff where name = %s"
    # cursor.execute(query, staff)
    # staff_id = cursor.fetchone()[0]

    # sql = "insert into movie_staff(movie_id, staff_id) values(%s, %s)"
    # cursor.execute(sql, (str(movie_id), str(staff_id)))
    # conn.commit()

conn.close()