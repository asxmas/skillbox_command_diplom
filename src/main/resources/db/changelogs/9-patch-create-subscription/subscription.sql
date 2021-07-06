-- liquibase formatted sql
-- changeset MikeVS:8-1-create-subscription

CREATE TABLE "public"."subscription"
(
    "id"    INTEGER      NOT NULL,
    "type" VARCHAR(100) NOT NULL,
	"person" INTEGER NOT NULL,
    CONSTRAINT "subscription_pkey" PRIMARY KEY ("id")
);
ALTER TABLE "public"."subscription"
    ADD CONSTRAINT "subscription_fk" FOREIGN KEY ("person") REFERENCES "public"."person" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;


