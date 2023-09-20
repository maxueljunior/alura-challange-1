ALTER TABLE tb_videos ADD CONSTRAINT id_fk_categoria FOREIGN KEY(categoria_id) REFERENCES tb_categoria (id);
