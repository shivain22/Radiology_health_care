import { Button } from "@/components/ui/button";
import { DialogClose } from "@/components/ui/dialog";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useValidatedForm } from "@/hooks/useValidatedForm";
import { EquipmentsData } from "@/schema/equipments";
import {
  formData,
  TestCategoryData,
  TestCategoryform,
} from "@/schema/testcategory";
import {
  getAuthToken,
  getEquipmentsByClient,
  getParentTestCategories,
} from "@/server_actions/(get-requests)/client/clientside";
import { createTestCategoryAction } from "@/server_actions/actions/testcategory";

import { zodResolver } from "@hookform/resolvers/zod";
import { RefreshCcwDot } from "lucide-react";
import { useEffect, useState } from "react";
import { useFormStatus } from "react-dom";
import { useForm } from "react-hook-form";

const TestCategoryForm = () => {
  const { errors, hasErrors, setErrors, handleChange } =
    useValidatedForm<TestCategoryData>(formData);

  const [getData, setGetData] = useState<boolean>(false);
  const [gotEquipments, setGotEquipments] = useState<EquipmentsData[]>([]);
  const [gotParentsCategory, setGotParentsCategory] = useState<
    TestCategoryData[]
  >([]);
  const [token, setToken] = useState("");

  const durations = [15, 30, 45, 60, 75, 90];
  const form = useForm<TestCategoryform>({
    resolver: zodResolver(formData),
    defaultValues: {
      testName: "",
      equipmentId: "null",
      parentTestCategoryId: "",
      testDuration: "",
    },
  });
  const editing = !form.formState.isValid;

  const handleSubmit = async (values: TestCategoryform) => {
    try {
      const payload = {
        testName: values.testName,
        equipmentId: Number(values.equipmentId),
        parentTestCategoryId: Number(values.parentTestCategoryId),
        testDuration: Number(values.testDuration),
      };
      await createTestCategoryAction(payload);
    } catch (e) {
      // setErrors(e);
      console.log(e);
    }
  };

  useEffect(() => {
    const user = localStorage.getItem("username");
    const pass = localStorage.getItem("password");

    if (getData == true) {
      const fetchToken = async () => {
        const token = await getAuthToken(user, pass);
        const tokenGet = token.id_token;
        setToken(tokenGet);
      };
      if (!!token) {
        const fetchEquipments = async () => {
          const equipments = await getEquipmentsByClient(token);
          setGotEquipments(equipments);
        };
        const fetchParentTestCategories = async () => {
          const parentTestCategories = await getParentTestCategories(token);
          setGotParentsCategory(parentTestCategories);
        };
        fetchEquipments();
        fetchParentTestCategories();
      }
      fetchToken();
      setGetData(false);
    }
  }, [getData, token]);

  return (
    <div className="mx-auto px-5">
      <Form {...form}>
        <form onSubmit={form.handleSubmit(handleSubmit)} className="space-y-3">
          <FormField
            control={form.control}
            name="testName"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Please provide a name for the Test</FormLabel>
                <FormControl>
                  <Input placeholder="Test Name" {...field} />
                </FormControl>
              </FormItem>
            )}
          />
          <div className="flex gap-4">
            <FormField
              control={form.control}
              name="equipmentId"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Please select the eqipment for the test</FormLabel>
                  <FormControl>
                    <Select onValueChange={field.onChange} value={field.value}>
                      <SelectTrigger>
                        <SelectValue placeholder="Select the equipment"></SelectValue>
                      </SelectTrigger>
                      <SelectContent>
                        {gotEquipments.map((equipment: EquipmentsData) => (
                          <SelectItem
                            key={equipment.id}
                            value={equipment.id.toString()}
                          >
                            {equipment.name}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </FormControl>
                </FormItem>
              )}
            />
            <div className="flex items-end ">
              <div
                onClick={() => setGetData(true)}
                className="bg-green-600 hover:bg-green-500 text-white font-bold py-2 px-6 rounded-lg hover:cursor-pointer"
              >
                <RefreshCcwDot className="w-4 h-4" />
              </div>
            </div>
          </div>
          <FormField
            control={form.control}
            name="parentTestCategoryId"
            render={({ field }) => (
              <FormItem>
                <FormLabel>
                  Please select the parent category for the test
                </FormLabel>
                <FormControl>
                  <Select onValueChange={field.onChange} value={field.value}>
                    <SelectTrigger>
                      <SelectValue placeholder="Select the parent category"></SelectValue>
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="0">Test Is Parent</SelectItem>
                      {gotParentsCategory.map((category: TestCategoryData) => (
                        <SelectItem
                          key={category.id}
                          value={category.id.toString()}
                        >
                          {category.testName}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </FormControl>
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="testDuration"
            render={({ field }) => (
              <FormItem>
                <FormLabel>
                  Please provide the duration of the test in hours
                </FormLabel>
                <FormControl>
                  <Select onValueChange={field.onChange} value={field.value}>
                    <SelectTrigger>
                      <SelectValue placeholder="Select the duration"></SelectValue>
                    </SelectTrigger>
                    <SelectContent>
                      {durations.map((duration) => (
                        <SelectItem key={duration} value={duration.toString()}>
                          {duration}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </FormControl>
              </FormItem>
            )}
          />
          <SaveButton errors={hasErrors} editing={editing} />
        </form>
      </Form>
    </div>
  );
};

export default TestCategoryForm;

const SaveButton = ({
  editing,
  errors,
}: {
  editing?: boolean;
  errors?: boolean;
}) => {
  const { pending } = useFormStatus();
  const isCreating = pending && editing === false;
  const isUpdating = pending && editing === true;

  return (
    <div className="mt-4">
      {editing ? (
        <div>
          <Button
            type="submit"
            className="w-64"
            disabled={isCreating || isUpdating || errors}
            aria-disabled={isCreating || isUpdating || errors}
          >
            {editing
              ? `Sav${isUpdating ? "ing..." : "e"}`
              : `Creat${isCreating ? "ing..." : "e"} `}
          </Button>
        </div>
      ) : (
        <div>
          <DialogClose asChild>
            <div>
              <Button
                type="submit"
                className="w-64"
                disabled={isCreating || isUpdating || errors}
                aria-disabled={isCreating || isUpdating || errors}
              >
                {editing
                  ? `Sav${isUpdating ? "ing..." : "e"}`
                  : `Creat${isCreating ? "ing..." : "e"} `}
              </Button>{" "}
            </div>
          </DialogClose>
        </div>
      )}
    </div>
  );
};
